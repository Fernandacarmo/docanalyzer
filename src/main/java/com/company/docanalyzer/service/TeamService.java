package com.company.docanalyzer.service;

import com.company.docanalyzer.model.Document;
import com.company.docanalyzer.model.Team;
import com.company.docanalyzer.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private DocumentService documentService;

    public List<Team> getTeamsWithUsers() {
        List<Team> teams = teamRepository.findUsersByTeam();
        return teams;
    }

    public List<Document> getDocumentsByTeam(String teamName) {
        Assert.notNull(teamName, "teamName should not be null");

        return documentService.getDocumentsByTeam(teamName);
    }
}
