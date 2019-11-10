package com.cg.bugtrackingsystem.service;

import java.io.IOException;
import java.net.URI;

import javax.tools.SimpleJavaFileObject;

public class SampleSource extends SimpleJavaFileObject {
	
	private String source;

    protected SampleSource(String name, String code) {
        super(URI.create("string:///" +name.replaceAll("\\.", "/") + Kind.SOURCE.extension), Kind.SOURCE);
        this.source = code ;
        System.out.println("inside sample source sonstructor");
    }

    @Override
    public CharSequence getCharContent(boolean ignoreEncodingErrors)
            throws IOException {
    	System.out.println("inside sample source");
        return source ;
    }

}
