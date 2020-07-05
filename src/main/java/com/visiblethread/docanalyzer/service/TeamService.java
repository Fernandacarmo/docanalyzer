package com.visiblethread.docanalyzer.service;

import com.visiblethread.docanalyzer.model.Document;
import com.visiblethread.docanalyzer.model.Team;
import com.visiblethread.docanalyzer.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return documentService.getDocumentsByTeam(teamName);
    }
}
