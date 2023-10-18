package persistence.core;

public class EntityFinder {

    public EntityFinder() {
    }
    public void findEntity() {
        Package[] packages = Package.getPackages();
        System.out.println(packages.toString());


    }


}
