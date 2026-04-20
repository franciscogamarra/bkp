package br.utils;

import java.awt.AWTException;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MouseJiggler implements AutoCloseable {
    private final Robot robot;
    private final ScheduledExecutorService scheduler;
    private final Rectangle allScreensBounds;
    private final Random rnd = new Random();

    // alterna: true = vai para (10,10); false = vai para ponto aleatório
    private volatile boolean nextIsFixed = true;

    public MouseJiggler() throws AWTException {
        this.robot = new Robot();
        this.scheduler = Executors.newSingleThreadScheduledExecutor(r -> {
            Thread t = new Thread(r, "mouse-jiggler");
            t.setDaemon(true);
            return t;
        });
        this.allScreensBounds = computeAllScreensBounds();
    }

    public void start(long period, TimeUnit unit) {
        // Dispara agora e repete a cada "period"
        scheduler.scheduleAtFixedRate(this::moveAlternating, 0, period, unit);
    }

    private void moveAlternating() {
        try {
            Point from = MouseInfo.getPointerInfo().getLocation();
            Point to;
            if (nextIsFixed) {
                // (10,10) relativo ao retângulo unificado dos monitores
                int x = allScreensBounds.x + 10;
                int y = allScreensBounds.y + 10;
                // garante que está dentro da área
                x = Math.max(allScreensBounds.x, Math.min(x, allScreensBounds.x + allScreensBounds.width  - 1));
                y = Math.max(allScreensBounds.y, Math.min(y, allScreensBounds.y + allScreensBounds.height - 1));
                to = new Point(x, y);
            } else {
                to = randomPointIn(allScreensBounds);
            }

            smoothMove(from, to, 1500); // 1,5s de animação
            nextIsFixed = !nextIsFixed; // alterna para a próxima execução
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void smoothMove(Point from, Point to, long durationMs) throws InterruptedException {
        int steps = 60; // ~60 frames
        long sleep = durationMs / steps;
        for (int i = 1; i <= steps; i++) {
            double t = (double) i / steps;
            double t2 = 0.5 - 0.5 * Math.cos(Math.PI * t); // ease-in-out

            int x = (int) Math.round(from.x + (to.x - from.x) * t2);
            int y = (int) Math.round(from.y + (to.y - from.y) * t2);
            robot.mouseMove(x, y);
            Thread.sleep(sleep);
        }
    }

    private Point randomPointIn(Rectangle r) {
        int x = r.x + rnd.nextInt(Math.max(1, r.width));
        int y = r.y + rnd.nextInt(Math.max(1, r.height));
        return new Point(x, y);
    }

    private static Rectangle computeAllScreensBounds() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] devices = ge.getScreenDevices();
        Rectangle union = new Rectangle(0, 0, 0, 0);
        for (GraphicsDevice d : devices) {
            Rectangle b = d.getDefaultConfiguration().getBounds();
            if (union.width == 0 && union.height == 0) {
                union = new Rectangle(b);
            } else {
                union = union.union(b);
            }
        }
        return union;
    }

    @Override
    public void close() {
        scheduler.shutdownNow();
    }

    public static void main(String[] args) throws Exception {
        MouseJiggler j = new MouseJiggler();
        Runtime.getRuntime().addShutdownHook(new Thread(j::close));
        j.start(4, TimeUnit.MINUTES); // alterna a cada 15s
        Thread.currentThread().join();
    }
}
