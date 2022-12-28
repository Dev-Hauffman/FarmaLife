package lines;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import lines.NPCLine.AgeEnum;
import lines.NPCLine.DnaSexEnum;
import lines.NPCLine.WaitingTimeEnum;
import state.counter.StagesEnum;

public class LineLoader {
    public LinesCatalog loadLines(LinesCatalog catalog){
        Path path2 = Paths.get("resources/lines/lines.txt");
        String aux[];
        Line line = new Line();
        try (BufferedReader reader = Files.newBufferedReader(path2, Charset.defaultCharset())) {
            String row = null;
            while ((row = reader.readLine()) != null) {
                aux = row.split(":");
                if (aux[0].equals("type")) {
                    if (aux[1].equals("player")) {
                        line = new PlayerLine();
                    }else{
                        line = new NPCLine();
                    }
                }
                if (aux[0].equals("id")) {
                    line.setId(Integer.parseInt(aux[1]));
                }
                if (aux[0].equals("parentid")) {
                    String[] parentids = aux[1].split(",");
                    for (int i = 0; i < parentids.length; i++) {
                        if (parentids[i].equals("none")) {
                            line.getParentId().add(-1);
                        }else{
                            line.getParentId().add(Integer.parseInt(parentids[i]));
                        }
                    }
                }
                if (aux[0].equals("nodelevel")) {
                    line.setNodeLevel(Integer.parseInt(aux[1]));
                }
                if (aux[0].equals("stage")) {
                    if (aux[1].equals("greeting")) {
                        line.setStage(StagesEnum.GREETING);
                    }else if (aux[1].equals("ordering")) {
                        line.setStage(StagesEnum.ORDERING);
                    }else if (aux[1].equals("questioning")) {
                        line.setStage(StagesEnum.QUESTIONING);
                    }else if (aux[1].equals("leaving")) {
                        line.setStage(StagesEnum.LEAVING);
                    }else{
                        try {
                            throw new Exception(aux[1] + " is not a valid stage");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                if (aux[0].equals("initialline")) {
                    line.setInitialLine(Boolean.parseBoolean(aux[1]));
                }
                if (aux[0].equals("tags")) {
                    continue;
                }
                if (aux[0].equals("engline")) {
                    String[] engline = aux[1].split(" ");
                    for (int i = 0; i < engline.length; i++) {
                        line.getEngLine().add(engline[i]);
                    }
                }
                if (aux[0].equals("ptline")) {
                    String[] ptline = aux[1].split(" ");
                    for (int i = 0; i < ptline.length; i++) {
                        line.getPtLine().add(ptline[i]);
                    }
                }
                if (aux[0].equals("responsesid")) {
                    String[] responseids = aux[1].split(",");
                    for (int i = 0; i < responseids.length; i++) {
                        if (responseids[i].equals("none")){
                            line.getReponsesId().add(-1);
                            break;
                        }
                        line.getReponsesId().add(Integer.parseInt(responseids[i]));
                    }
                }
                if (line instanceof PlayerLine) {
                    if (aux[0].equals("satisfactionrating")) {
                        ((PlayerLine)line).setSatisfactionRating(Integer.parseInt(aux[1]));
                    }
                    if (aux[0].equals("quickanswer")) {
                        ((PlayerLine)line).setQuickAnswer(Boolean.parseBoolean(aux[1]));
                    }
                    if (aux[0].equals("streaklines")) {
                        String[] streaklines = aux[1].split(",");
                        for (int i = 0; i < streaklines.length; i++) {
                            if (streaklines[i].equals("none")){
                                ((PlayerLine)line).getStreakLines().add(-1);
                                break;
                            }
                            ((PlayerLine)line).getStreakLines().add(Integer.parseInt(streaklines[i]));
                        }
                    }
                }else{
                    if (aux[0].equals("requiredprofile")) {
                        String[] requiredprofile = aux[1].split(",");
                        for (int i = 0; i < requiredprofile.length; i++) {
                            ((NPCLine)line).getRequiredProfile().add(requiredprofile[i]);
                        }
                    }
                    if (aux[0].equals("targetsatisfaction")) {
                        ((NPCLine)line).setTargetSatisfaction(Integer.parseInt(aux[1]));
                    }
                    if (aux[0].equals("requirewaiting")) {
                        if (aux[1].equals("none")) {
                            ((NPCLine)line).setRequiredWait(WaitingTimeEnum.NONE);
                        }else if (aux[1].equals("short")) {
                            ((NPCLine)line).setRequiredWait(WaitingTimeEnum.SHORT);
                        }else if (aux[1].equals("long")) {
                            ((NPCLine)line).setRequiredWait(WaitingTimeEnum.LONG);
                        }else{
                            try {
                                throw new Exception(aux[1] + " is not a valid waitingTimeEnum");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    if (aux[0].equals("dnasex")) {
                        if (aux[1].equals("any")) {
                            ((NPCLine)line).setDnaSex(DnaSexEnum.ANY);
                        }else if (aux[1].equals("male")) {
                            ((NPCLine)line).setDnaSex(DnaSexEnum.MALE);
                        }else if (aux[1].equals("female")) {
                            ((NPCLine)line).setDnaSex(DnaSexEnum.FEMALE);
                        }else{
                            try {
                                throw new Exception(aux[1] + " is not a valid dnaSexEnum");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
                if (aux[0].equals("age")) {
                    if (aux[1].equals("any")) {
                        ((NPCLine)line).setAge(AgeEnum.ANY);
                    }else if (aux[1].equals("adult")) {
                        ((NPCLine)line).setAge(AgeEnum.ADULT);
                    }else if (aux[1].equals("senior")) {
                        ((NPCLine)line).setAge(AgeEnum.SENIOR);
                    }else{
                        try {
                            throw new Exception(aux[1] + " is not a valid dnaSexEnum");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                if (aux[0].equals("relatedtopics")) {
                    
                }
                if (aux[0].equals("}")) {
                    catalog.getFullLineList().add(line);
                    if (line.isInitialLine()) {
                        catalog.getStartingList().add(line);
                    }
                }
            }
        } catch (IOException e) {
            System.err.format("Erro na leitura do arquivo: ", e);
        }

        return catalog;
    }
}
