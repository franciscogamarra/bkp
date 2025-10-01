package gm.languages.ts.angular.core;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import gm.languages.ts.javaToTs.annotacoes.DefaultValue;
import gm.languages.ts.javaToTs.annotacoes.From;
import gm.languages.ts.javaToTs.annotacoes.ImportStatic;

@ImportStatic
@From("@angular/core")
@DefaultValue("{providedIn: 'root'}")
@Retention(RetentionPolicy.RUNTIME)
public @interface Injectable {}