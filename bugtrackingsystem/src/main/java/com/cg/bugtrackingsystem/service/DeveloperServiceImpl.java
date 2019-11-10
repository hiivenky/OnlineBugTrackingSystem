package com.cg.bugtrackingsystem.service;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Locale;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.ToolProvider;

import org.springframework.stereotype.Service;


/**
 *author: Venkatesh
 *Description : This class provides the service for developer
 *created Date: 10/11/2019
 *last modified : 10/11/2019     
 */
@Service("DeveloperService")
public class DeveloperServiceImpl implements DeveloperService {

	@Override
	public boolean submit() {
		
		return false;
	}
	/**
	 *author: Venkatesh
	 *Description : This method is used for compiling the code 
	 *created Date: 10/11/2019
	 *last modified : 10/11/2019     
	 *Input : String code
	 *Output : ResponseEntity         
	 */
	@Override
	public DiagnosticCollector<JavaFileObject> compile(String code) {
		System.out.println(code);
		
		SimpleJavaFileObject fileObject = new SampleSource("com.test.Test",code);
		JavaFileObject javaFileObjects[] = new JavaFileObject[] { fileObject};
		Iterable<? extends JavaFileObject> compilationUnits = Arrays
                .asList(javaFileObjects);
		Iterable<String> compilationOptionss = Arrays.asList(new String[] {
                "-d", "classes" });
		System.out.println(System.getProperty("java.home"));
		 DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
	        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
	        System.out.println(compiler);

	        JavaFileManager fileManager = compiler.getStandardFileManager(
	                diagnostics, Locale.getDefault(), Charset.defaultCharset());
	        System.out.println("after filemanager");
	        JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnostics,
	                compilationOptionss, null, compilationUnits);
	        System.out.println("after compile task");
	        boolean status = task.call();
	        //String args1[] = buildCompileJavacArgs(srcFiles);
	        System.out.println("status"+status);
	        if(!status)
	        {
	            System.out.println("Found errors in compilation");
	            int errors = 1;
	            for(Diagnostic diagnostic : diagnostics.getDiagnostics())
	            {
	                printError(errors, diagnostic);
	                errors++;
	            }
	        }
	        else
	            System.out.println("Compilation sucessfull");
	        
	        try
	        {
	            fileManager.close();
	        } catch (IOException e){}
		return null;
	}
	public static void printError(int number,Diagnostic diagnostic)
    {
        System.out.println();
        System.out.print(diagnostic.getKind()+"  : "+number+" Type : "+diagnostic.getMessage(Locale.getDefault()));
        System.out.print(" hii venky at column : "+diagnostic.getColumnNumber());
        System.out.println(" hii venky Line number : "+diagnostic.getLineNumber());
        System.out.println("hii venky Source : "+diagnostic.getSource());

    }

}
