package persistence.core;

import jakarta.persistence.Entity;
import persistence.Application;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ApplicationPathEntityTargetSourceLoader {

    private static final char PKG_SEPARATOR = '.';

    private static final char DIR_SEPARATOR = '/';

    private static final String CLASS_FILE_SUFFIX = ".class";

    private final EntityMetadataModelFactory entityMetadataModelFactory;

    public ApplicationPathEntityTargetSourceLoader() {
        this.entityMetadataModelFactory = new EntityMetadataModelFactory();
    }

    public EntityMetadataModels createEntityMetaDataModels(Class<?> applicationClass) {
        String packageName = applicationClass.getPackage().getName();

        String relationPath = packageName.replace(PKG_SEPARATOR, DIR_SEPARATOR);

        URL resource = ClassLoader.getSystemClassLoader().getResource(relationPath);

        File resourecs = new File(resource.getPath());

        List<Class<?>> targets = new ArrayList<>();

        File[] files = resourecs.listFiles();

        if (files == null) {
            return entityMetadataModelFactory.empty();
        }

        for (File file : files) {
            List<Class<?>> findResults = findEntityClasses(file, relationPath);

            if (findResults == null) {
                continue;
            }

            targets.addAll(findResults);
        }

        return entityMetadataModelFactory.createEntityMetadataModels(targets);
    }

    private List<Class<?>> findEntityClasses(File file, String scannedPackage) {
        List<Class<?>> entityClasses = new ArrayList<>();
        String resource = scannedPackage + PKG_SEPARATOR + file.getName();
        if (file.isDirectory()) {
            File[] files = file.listFiles();

            if (files == null) {
                return null;
            }

            for (File child : files) {
                List<Class<?>> findResults = findEntityClasses(child, resource);

                if (findResults == null) {
                    return null;
                }

                entityClasses.addAll(findResults);
            }
        } else if (resource.endsWith(CLASS_FILE_SUFFIX)) {
            int endIndex = resource.length() - CLASS_FILE_SUFFIX.length();
            String classPath = resource.substring(0, endIndex).replace("/", ".");

            try {
                Class<?> target = Class.forName(classPath);

                if (target.isAnnotationPresent(Entity.class)) {
                    entityClasses.add(target);
                }

            } catch (ClassNotFoundException ignore) {
                throw new IllegalArgumentException("not found class : " + classPath);
            }
        }

        return entityClasses;
    }
}
