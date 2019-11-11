package com.cg.bugtrackingsystem.service;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.ToolProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.bugtrackingsystem.dto.Bug;
import com.cg.bugtrackingsystem.dto.Developer;
import com.cg.bugtrackingsystem.dto.Ticket;
import com.cg.bugtrackingsystem.repository.BugRepository;
import com.cg.bugtrackingsystem.repository.DeveloperRepository;
import com.cg.bugtrackingsystem.repository.TicketRepository;


/**
 *author: Venkatesh
 *Description : This class provides the service for developer
 *created Date: 10/11/2019
 *last modified : 10/11/2019     
 */
@Service("DeveloperService")
public class DeveloperServiceImpl implements DeveloperService {
	@Autowired
	BugRepository bugDao;
	@Autowired
	DeveloperRepository developerDao;
	@Autowired
	TicketRepository ticketDao;
	/**
	 *author: Venkatesh
	 *Description : This method is used for submitting the code 
	 *created Date: 11/11/2019
	 *last modified : 11/11/2019     
	 *Input : bugId,developerId,code
	 *Output : boolean         
	 */
	@Override
	public boolean submit(int bugId,int developerId,String code) {
		System.out.println("Inside submit");
		Bug bug = bugDao.findBybugId(bugId);
		Ticket ticket = bug.getTicket();
		ticket.setCodeSnippet(code);
		ticket.setTicketStatus("completed");
		bug.setBugStatus("completed");
		Developer developer = developerDao.findByEmployeeId(developerId);
		developer.setAssignStatus(false);
		developer.setTicketAssigned(null);
		bugDao.save(bug);
		developerDao.save(developer);
		ticketDao.save(ticket);
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
	public List<String> compile(String code) {
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
	        List<String> bugs= new ArrayList<String>();
	        if(!status)
	        {
	            System.out.println("Found errors in compilation");
	            int errors = 1;
	            for(Diagnostic diagnostic : diagnostics.getDiagnostics())
	            {
	                printError(errors, diagnostic);
	                bugs.add(diagnostic.getKind()+"  : "+errors+" Type : "+diagnostic.getMessage(Locale.getDefault()));
	                bugs.add(diagnostic.getColumnNumber()+"");
	                bugs.add(diagnostic.getSource()+"");
	                errors++;
	            }
	        }
	        else {
	        	bugs.add("Compilation sucessfull");
	        	System.out.println("Compilation sucessfull");
	        	return bugs;
	        }
	        try
	        {
	            fileManager.close();
	        } catch (IOException e){}
		return bugs;
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
