package patrologia.translator.declension;

import patrologia.translator.basicelements.Language;
import patrologia.translator.casenumbergenre.Gender;

import java.util.List;
import java.util.Map;

/**
 * Created by Laurent KLOEBLE on 23/08/2015.
 */
public abstract class DeclensionFactory {

    protected String declensionsAndFiles;
    protected String declensionPath;
    protected Map<String, String> declensions;
    protected Map<String, Declension> declensionMap;
    protected Language language;
    protected Gender gender;
    protected String root;

    public abstract Declension getDeclensionByPattern(String declensionPattern);

    /*
    protected void populateDeclensionMap(List<String> declensionsDefinitions) {
        declensionsDefinitions.stream().filter(line -> line != null && line.length() > 0 && line.indexOf("%") > 0).
        forEach(line -> declensions.put(getDeclensionName(line), getDeclensionFile(line)));
    }
    */

    protected boolean isCustomPluralAndConstructedState(String declensionPattern) {
        return declensionPattern.startsWith("custom(") && declensionPattern.endsWith(")");
    }

    private String getDeclensionFile(String line) {
        return line.split("%")[1];
    }

    private String getDeclensionName(String line) {
        return line.split("%")[0].toLowerCase();
    }

    public Language getLanguage() {
        return language;
    }

    public void setTemporaryGenderAndRoot(Gender gender, String root) {
        this.gender = gender;
        this.root = root;
    }

    protected void populateDeclensionMap(List<String> definitions, List<Declension> declensionList) {
        definitionLoop : for(String definitionAndFile : definitions) {
            String[] parts = definitionAndFile.split("%");
            String definition = parts[0];
            String file = parts[1];
            declensionLoop : for(Declension declension : declensionList) {
                if(file.equals(declension.endingsFilePath)) {
                    declensionMap.put(definition,declension);
                    break declensionLoop;
                }
            }
        }
    }

}
