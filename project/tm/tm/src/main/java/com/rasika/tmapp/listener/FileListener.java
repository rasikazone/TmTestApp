package com.rasika.tmapp.listener;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.rasika.tmapp.model.Task;
import com.rasika.tmapp.model.Team;
import com.rasika.tmapp.model.TeamSkill;
import com.rasika.tmapp.model.TeamSkillPK;
import com.rasika.tmapp.repo.TaskRepo;
import com.rasika.tmapp.repo.TeamRepo;
import com.rasika.tmapp.repo.TeamSkillRepo;

@Component
public class FileListener extends Thread{
	
	
	private static String FIRSTLINE_TASK = "\"TASK_ID\",\"SKILL\"";
	private static String FIRSTLINE_TEAM = "\"TEAM_ID\"";
	private static String FIRSTLINE_TEAM_SKILL = "\"TEAM_ID\",\"SKILL\"";
	private static final String CSV_FILE_PATH = "D:/MyWork/TM/CSV/";

	
	private static ExecutorService executor = Executors.newFixedThreadPool(10);

	@Autowired
	private TeamRepo teamRepo;
	
	@Autowired
	private TaskRepo taskRepo;
	
	@Autowired
    private TeamSkillRepo teamSkillRepo;

	@EventListener
    public void appReady(ApplicationReadyEvent eve) {

		try {
	    	
	    	System.out.println("teamRepo " + (null == teamRepo));
	    	System.out.println("taskRepo " + (null == taskRepo));
	    	System.out.println("teamSkillRepo " + (null == teamSkillRepo));
	    	
	        WatchService watcher = FileSystems.getDefault().newWatchService();
	        System.out.println("Derectory Path " + CSV_FILE_PATH);
	        Path dir = Paths.get(CSV_FILE_PATH);
	        dir.register(watcher, ENTRY_CREATE);
	
	        System.out.println("Watch Service registered for dir: " + dir.getFileName());
	
	        while (true) {
	            WatchKey key;
	            try {
	                key = watcher.take();
	            } catch (InterruptedException ex) {
	                return;
	            }
	
	            for (WatchEvent<?> event : key.pollEvents()) {
	                WatchEvent.Kind<?> kind = event.kind();
	
	                @SuppressWarnings("unchecked")
	                WatchEvent<Path> ev = (WatchEvent<Path>) event;
	                Path fileName = ev.context();
	
	                System.out.println(kind.name() + ": " + fileName);
	                executor.execute(new FileProcessoer(CSV_FILE_PATH + fileName));
	
	            }
	
	            boolean valid = key.reset();
	            if (!valid) {
	                break;
	            }
	        }
	
	    } catch (IOException ex) {
	        System.err.println(ex);
	    }
    }
	
	
	class FileProcessoer extends Thread {
		
		private String filepath;
		
		public FileProcessoer(String filepath) {
			this.filepath = filepath;
		}

		public void run() {
		
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			System.out.println(filepath);
			
			Path path = Paths.get(filepath);
			
			
			
			try{

			  List<String> contents = Files.readAllLines(path);
			  
			  String fileIdentity = contents.remove(0);
			  
			  if(FIRSTLINE_TASK.equals(fileIdentity)){
				  System.out.println("Processing Task File");
				  for(String content:contents){
					  if(null != content) {
					    String[]fields = content.split(",");
					    if(fields.length == 2) {
					    	Task task = new Task();
						  	task.setTaskId(getColoumnValue(fields[0]));
							task.setSkill(getColoumnValue(fields[1]));
							taskRepo.save(task);
					    }
					  }
				  }
				  System.out.println("Processing Task File Completed");

			  }else if (FIRSTLINE_TEAM.equals(fileIdentity)) {
				  System.out.println("Processing Team File");
				  for(String content:contents){
					  if(null != content) {
						    Team team = new Team();
						  	team.setTeamId( getColoumnValue(content));
						    teamRepo.save(team);
						  }
					 }
				  System.out.println("Processing Team File commpleted");

			  }else if (FIRSTLINE_TEAM_SKILL.equals(fileIdentity)) {
				  System.out.println("Processing TeamSkil File");
				  for(String content:contents){
					  if(null != content) {
					    String[]fields = content.split(",");
					    if(fields.length == 2) {
					    	TeamSkill teamSkill = new TeamSkill();
					    	TeamSkillPK teamSkillPk = new TeamSkillPK();
					    	teamSkillPk.setTeamId(getColoumnValue(fields[0]));
					    	teamSkillPk.setSkill(getColoumnValue(fields[1]));
					    	teamSkill.setTeamSkillPK(teamSkillPk);
							teamSkillRepo.save(teamSkill);
					    }
					  }
				  }
				  System.out.println("Processing TeamSkil File Completed");
			  }
			  
		
			Files.delete(path);
				

			}catch(IOException ex){
			  ex.printStackTrace();
			}
			
		}
		
		private String getColoumnValue(String fileValue) {
			if(null != fileValue) {
				return fileValue.replaceAll("\"","").trim();
			}
			return null;
		}
		
	}

}
