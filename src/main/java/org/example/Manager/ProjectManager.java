package org.example.Manager;
import org.example.Database.project.ProjectRepository;
import org.example.Database.user.UserRepository;
import org.example.Model.Project;
import org.example.Model.User;
import org.example.Model.UserRole;

import java.util.List;

public class ProjectManager {
    private static ProjectManager instance = null;
    private ProjectRepository projectRepository = new ProjectRepository();

    public ProjectManager() {
    }

    public static ProjectManager getInstance() {
        if (instance == null)
            instance = new ProjectManager();
        return instance;
    }
    public boolean hasAnyProject() {
        return projectRepository.hasAnyProject();
    }
    public void addProject(String name, String description) {
            Project project = new Project(name, description);
            projectRepository.create(project);
        }
    public void editProject(int id, String name, String description) {
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


