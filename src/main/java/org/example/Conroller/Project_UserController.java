package org.example.Conroller;

import org.example.Database.project_user.Project_UserRepository;
import org.example.Model.Project;
import org.example.Model.User;
import java.util.List;

public class Project_UserController {

    private static Project_UserController instance = null;
    private final Project_UserRepository projectUserRepository = new Project_UserRepository();

    private Project_UserController(){
    }

    public boolean addUserToProject(User user, Project project) {
        if (user == null || project == null) {
            return false;
        }

        int userId = user.getId();
        int projectId = project.getId();

        return projectUserRepository.addUserToProject(userId, projectId);
    }

    public boolean removeUserFromProject(User user, Project project) {
        if (user == null || project == null) {
            return false;
        }

        int userId = user.getId();
        int projectId = project.getId();

        return projectUserRepository.removeUserFromProject(userId, projectId);
    }

    public List<User> getUsersByProject(Project project) {
        if (project == null) {
            return null;
        }

        int projectId = project.getId();

        return projectUserRepository.getUsersByProject(projectId);
    }

    public List<Project> getsProjectsByUser() {
        User user = UserController.getInstance().getCurrentUser();
        if (user == null) {
            return null;
        }

        int userId = user.getId();

        return projectUserRepository.getProjectsByUser(userId);
    }

    public static Project_UserController getInstance() {
        if (instance == null)
            instance = new Project_UserController();
        return instance;
    }

}
