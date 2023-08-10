package org.example.Conroller;
import org.example.Model.UserRole;
public class PermissionController {
    private static UserRole getRole() {
        if (UserController.getInstance().getCurrentUser() == null) {
            return null;
        }
        return UserController.getInstance().getCurrentUser().getRole();
    }

    public static boolean showAddProject() {
        return PermissionController.getRole() != null && PermissionController.getRole() == UserRole.SUPER_ADMIN;
    }

    public static boolean showMembers()  {
        return PermissionController.getRole() != null && PermissionController.getRole() == UserRole.SUPER_ADMIN;
    }

    public static boolean showAllProjects()  {
        return PermissionController.getRole() != null && PermissionController.getRole() == UserRole.SUPER_ADMIN;
    }

    public static boolean deleteAndEditProject()  {
        return PermissionController.getRole() != null && PermissionController.getRole() == UserRole.SUPER_ADMIN;
    }

    public static boolean addDeleteAndEditIssue()  {
        return PermissionController.getRole() != null && (PermissionController.getRole() == UserRole.SUPER_ADMIN || PermissionController.getRole() == UserRole.PO);
    }

    public static boolean addDeleteAndEditBoard()  {
        return PermissionController.getRole() != null && (PermissionController.getRole() == UserRole.SUPER_ADMIN || PermissionController.getRole() == UserRole.PO);
    }
}
