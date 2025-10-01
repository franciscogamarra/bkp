package gm.utils.test;

import java.util.HashMap;
import java.util.Map;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import js.support.console;

public class TScriptEngineManager {

    public static void main(String[] args) {
        ScriptEngineManager factory = new ScriptEngineManager();
        ScriptEngine engine = factory.getEngineByName("JavaScript");

        try {
            engine.put("aParam", 5);
            engine.put("bParam", 10);
            engine.eval( "function ola(a, b) { return a + b; }" );
            engine.eval("print('Resultado: '+ ola(aParam, bParam) )");
            engine.eval("var s = ola(aParam, bParam)");

            // create the engine and have it load your javascript
            Bindings bind = engine.getBindings(ScriptContext.ENGINE_SCOPE);

            for ( String attr : bind.keySet() ) {
                console.log( attr );
            }

            Map<String, Integer> m = new HashMap<>();
            m.put("c", 10);
            engine.put("m", m);

            engine.eval("var x = m.get('c');");
            console.log("Max num: " + engine.get("x"));

        } catch (ScriptException e) {
            e.printStackTrace();
        }
    }

}
