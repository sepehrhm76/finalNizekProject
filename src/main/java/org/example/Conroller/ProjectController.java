package org.example.Conroller;

import org.example.Database.project.ProjectRepository;
import org.example.Model.Project;
import java.util.List;

public class ProjectController {
    private static ProjectController instance = null;
    private ProjectRepository projectRepository = new ProjectRepository();

    public ProjectController() {
    }

    public static ProjectController getInstance() {
        if (instance == null)
            instance = new ProjectController();
        return instance;
    }
    public boolean hasAnyProject() {
        return projectRepository.hasAnyProject();
    }
    public void addProject(String name, String description) {
            Project project = new Project(name, description);
            projectRepository.create(project);
        }
    public void updateProject(int id, String name, String description) {
        Project project = new Project(name, description);
        projectRepository.update(id, project);
    }
    public void removeProject(Project project) {
        if (project != null) {
            projectRepository.delete(project);
        }
    }
    public List<Project> getAllProject() {
        return projectRepository.getAll();
    }



    }


