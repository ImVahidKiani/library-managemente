package model;

public class Admin {
    private String name;
    private String password;
    private static Admin admin = null;

    private Admin(String name, String password) {
        this.name = name.toLowerCase();
        this.password = password.toLowerCase();
    }

    public static Admin getAdmin() {
        if (admin == null) {
            admin = new Admin("admin", "admin");
        }
        return admin;
    }

    public static boolean authAdmin(String name, String password) {
        Admin localAdmin = Admin.getAdmin();
        return (localAdmin.name == null ? name == null : localAdmin.name.equals(name)) && (localAdmin.password == null ? password == null : localAdmin.password.equals(password));
    }
}
