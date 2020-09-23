package Bot;

import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.Update;


import java.util.ArrayList;

public class FileProcessing {
    public static ArrayList<String> arrayList = new ArrayList<>();
    public static final String wrongData = "Вышлите резюме в формате .pdf/.doc/.docx";


    public static Document takeFile(Update update) {
        Document document =update.getMessage().getDocument();
        if (isFileSuits(document.getMimeType())) {
            update.getMessage();
        } else {
            return null;
        }
        return document;
    }

    public static boolean isFileSuits(String mimeType) {
        if (mimeType.contains("script")) {
            return false;
        }
        for (String s : arrayList) {
            if (mimeType.contains(s)) {
                return true;
            }
        }
        return false;
    }

    public static void init() {
        arrayList.add(".text");
        arrayList.add(".document");
        arrayList.add("pdf");
        arrayList.add(".pdf");
    }

}
