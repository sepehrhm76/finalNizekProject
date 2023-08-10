package org.example.Conroller;
import org.example.Model.UserRole;
public class PermissionController {
    private static UserRole getRole() {
        return UserController.getInstance().getCurrentUser().getRole();
    }
    public static boolean showAddProject() {
        return PermissionController.getRole() == UserRole.SUPER_ADMIN;
    }

    public static boolean showMembers()  {
        return PermissionController.getRole() == UserRole.SUPER_ADMIN;
    }
}
