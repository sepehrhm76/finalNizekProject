package org.example.Manager;
import org.example.Database.project.ProjectRepository;
import org.example.Model.Project;

public class ProjectManager {
    private static ProjectManager instance = null;

    public ProjectManager() {
    }

    public static ProjectManager getInstance() {
        if (instance == null)
            instance = new ProjectManager();
        return instance;
    }
}
