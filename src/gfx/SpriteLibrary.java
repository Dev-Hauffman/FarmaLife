package gfx;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import java.awt.*;

public class SpriteLibrary {

    private Map<String, SpriteSet> spriteSets;
    private Map<String, Image> images;

    public SpriteLibrary() {
        spriteSets = new HashMap<>();
        images = new HashMap<>();
        loadSpriteFromDisk();
        // System.out.println(spriteSets.keySet().toString());
        // System.out.println(images.keySet().toString());
        // spriteSets.get("nose").checkAnimationSheets();
    }

    private void loadSpriteFromDisk() {
        loadSpriteSets("/sprites/units");
        loadImages("/sprites/fonts");
        loadSpriteSets("/sprites/effects");
        loadImages("/sprites/scenery");
        loadSpriteSets("/sprites/bodyparts");
    }

    private void loadImages(String path) {
        String[] imagesInFolder = getImagesInFolder(path);
            for (String fileName : imagesInFolder) {
                images.put(
                    fileName.substring(0, fileName.length() - 4),
                    ImageUtils.loadImage(path + "/" + fileName)
                );
            }
    }

    private void loadSpriteSets(String path) {
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

            spriteSets.put(folderName, spriteSet);
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

    public SpriteSet getSpriteSets(String name) {
        return spriteSets.get(name);
    }

    public Image getImage(String name) {
        return images.get(name);
    }
    
}
