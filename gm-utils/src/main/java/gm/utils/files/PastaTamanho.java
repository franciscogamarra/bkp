package gm.utils.files;

import gm.utils.comum.Lst;

public class PastaTamanho {

    public static void main(String[] args) {
    	GFile pathRaiz = GFile.get("c:/dev");
    	Lst<GFile> files = pathRaiz.getAllFiles();
    	Lst<GFile> diretorios = files.filter(i -> i.isDirectory());
    	diretorios.forEachP(GFile::print);
    }

}
