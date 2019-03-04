package com.example.demoMail.controller;

import com.example.demoMail.filestorage.FileStorage;
import com.example.demoMail.model.FileInfo;
import com.example.demoMail.model.SendMail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class DownloadFileController {
	FileInfo f = new FileInfo();
	List<FileInfo>list= new ArrayList<>();
	@Autowired
	FileStorage fileStorage;
	
	/*
	 * Retrieve Files' Information
	 */
	@GetMapping("/files")
	public String getListFiles(Model model) {
		List<FileInfo> fileInfos = fileStorage.loadFiles().map(
					path ->	{
						String filename = path.getFileName().toString();
						String url = MvcUriComponentsBuilder.fromMethodName(DownloadFileController.class,
		                        "downloadFile", path.getFileName().toString()).build().toString();
						f.setFilename(filename);
						f.setUrl(url);
						list.add(f);
						return new FileInfo(filename, url);
					}
				)
				.collect(Collectors.toList());

		model.addAttribute("sendMail", new SendMail());
		model.addAttribute("files", fileInfos);
		return "listfiles";
	}

}