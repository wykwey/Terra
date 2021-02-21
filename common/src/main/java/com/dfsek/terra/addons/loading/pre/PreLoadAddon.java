package com.dfsek.terra.addons.loading.pre;

import com.dfsek.terra.addons.addon.TerraAddon;
import com.dfsek.terra.addons.annotations.Addon;
import com.dfsek.terra.addons.annotations.Depends;
import com.dfsek.terra.addons.loading.AddonLoadException;
import com.dfsek.terra.addons.loading.pre.exception.CircularDependencyException;
import com.dfsek.terra.addons.loading.pre.exception.DependencyMissingException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PreLoadAddon {
    private final List<PreLoadAddon> depends = new ArrayList<>();
    private final Class<? extends TerraAddon> addonClass;
    private final String id;
    private final String[] dependencies;

    public PreLoadAddon(Class<? extends TerraAddon> addonClass) {
        this.addonClass = addonClass;
        this.id = addonClass.getAnnotation(Addon.class).value();
        Depends depends = addonClass.getAnnotation(Depends.class);
        this.dependencies = depends == null ? new String[] {} : depends.value();
    }

    public List<PreLoadAddon> getDepends() {
        return depends;
    }

    public void rebuildDependencies(AddonPool pool, PreLoadAddon origin, boolean levelG1) throws AddonLoadException {
        if(this.equals(origin) && !levelG1)
            throw new CircularDependencyException("Detected circular dependency in addon \"" + id + "\", dependencies: " + Arrays.toString(dependencies));

        for(String dependency : dependencies) {
            PreLoadAddon preLoadAddon = pool.get(dependency);
            if(preLoadAddon == null)
                throw new DependencyMissingException("Dependency " + dependency + " was not found. Please install " + dependency + " to use " + id + ".");
            depends.add(preLoadAddon);
            preLoadAddon.rebuildDependencies(pool, origin, false);
        }
    }

    public String getId() {
        return id;
    }

    public Class<? extends TerraAddon> getAddonClass() {
        return addonClass;
    }
}
