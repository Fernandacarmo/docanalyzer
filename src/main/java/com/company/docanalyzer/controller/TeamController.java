package com.company.docanalyzer.controller;

import com.company.docanalyzer.model.Document;
import com.company.docanalyzer.model.Team;
import com.company.docanalyzer.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TeamController {

    @Autowired
    private TeamService teamService;

    @GetMapping(value = "/teams")
    public List<Team> getTeamsWithUsers() {
        return teamService.getTeamsWithUsers();
    }

    @GetMapping(value = "/team/{teamName}/documents")
    public List<Document> getDocumentsByTeam(@PathVariable("teamName") String teamName) {
        List<Document> list = teamService.getDocumentsByTeam(teamName);
        return list;
    }
}
