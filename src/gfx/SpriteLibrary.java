package gfx;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import java.awt.*;

public class SpriteLibrary {

    private Map<String, SpriteSet> units;
    private Map<String, Image> tiles;

    public SpriteLibrary() {
        units = new HashMap<>();
        tiles = new HashMap<>();
        loadSpriteFromDisk();
    }

    private void loadSpriteFromDisk() {
        loadUnits("/sprites/units");
        loadTiles("/sprites/units");
    }

    private void loadTiles(String path) {
        String[] imagesInFolder = getImagesInFolder(path);
            for (String fileName : imagesInFolder) {
                tiles.put(
                    fileName.substring(0, fileName.length() - 4),
                    ImageUtils.loadImage(path + "/" + fileName)
                );
            }
    }

    private void loadUnits(String path) {
        String[] folderNames = getFolderNames(path);

        for (String folderName : folderNames) {
            SpriteSet spriteSet = new SpriteSet();
            String pathToFolder = path + "/" + folderName;
            String[] sheetsInFolder = getImagesInFolder(pathToFolder);
            for (String sheetName : sheetsInFolder) {
                spriteSet.addSheet(
                    sheetName.substring(0, sheetName.length() - 4),
                    ImageUtils.loadImage(pathToFolder + "/" + sheetName)
                );
            }

            units.put(folderName, spriteSet);
        }
    }

    private String[] getImagesInFolder(String basePath) {
        URL resource = SpriteLibrary.class.getResource(basePath);
        File file = new File(resource.getFile());
        return file.list((current, name) -> new File(current, name).isFile());
    }

    private String[] getFolderNames(String basePath) {
        URL resource = SpriteLibrary.class.getResource(basePath);
        File file = new File(resource.getFile());
        return file.list((current, name) -> new File(current, name).isDirectory());
    }

    public SpriteSet getUnit(String name) {
        return units.get(name);
    }

    public Image getTile(String name) {
        return tiles.get(name);
    }
    
}
