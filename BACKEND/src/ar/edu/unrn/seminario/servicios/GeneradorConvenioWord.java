package ar.edu.unrn.seminario.servicios;

import java.io.*;
import java.util.Map;
import org.apache.poi.xwpf.usermodel.*;
public class GeneradorConvenioWord {
	   /**
     * Genera un archivo Word (.docx) a partir de una plantilla, reemplazando los campos {{clave}} por valores reales.
     * Guarda el archivo en disco (destino) y retorna el contenido como byte[] para guardar en base de datos.
     *
     * @param nombrePlantilla El nombre del archivo de plantilla (debe estar en resources)
     * @param datos Mapa de claves y valores a reemplazar
     * @param archivoDestino Archivo físico donde se guarda el resultado
     * @return byte[] del contenido del archivo generado
     * @throws IOException si hay un error de lectura/escritura
     */
    public static byte[] generar(String nombrePlantilla, Map<String, String> datos, File archivoDestino) throws IOException {
        try (InputStream is = GeneradorConvenioWord.class.getClassLoader().getResourceAsStream(nombrePlantilla)) {
            if (is == null) {
                throw new FileNotFoundException("No se encontró la plantilla: " + nombrePlantilla);
            }

            XWPFDocument doc = new XWPFDocument(is);
            reemplazarTexto(doc, datos);

            // Guardar en archivo físico
            try (FileOutputStream out = new FileOutputStream(archivoDestino)) {
                doc.write(out);
            }

            // Convertir a byte[]
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            doc.write(bos);
            doc.close();
            return bos.toByteArray();
        }
    }

    private static void reemplazarTexto(XWPFDocument doc, Map<String, String> datos) {
        for (XWPFParagraph p : doc.getParagraphs()) {
            for (XWPFRun run : p.getRuns()) {
                String text = run.getText(0);
                if (text != null) {
                    for (Map.Entry<String, String> entry : datos.entrySet()) {
                        if (text.contains("{{" + entry.getKey() + "}}")) {
                            text = text.replace("{{" + entry.getKey() + "}}", entry.getValue());
                            run.setText(text, 0);
                        }
                    }
                }
            }
        }

        for (XWPFTable table : doc.getTables()) {
            for (XWPFTableRow row : table.getRows()) {
                for (XWPFTableCell cell : row.getTableCells()) {
                    for (XWPFParagraph p : cell.getParagraphs()) {
                        for (XWPFRun run : p.getRuns()) {
                            String text = run.getText(0);
                            if (text != null) {
                                for (Map.Entry<String, String> entry : datos.entrySet()) {
                                    if (text.contains("{{" + entry.getKey() + "}}")) {
                                        text = text.replace("{{" + entry.getKey() + "}}", entry.getValue());
                                        run.setText(text, 0);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

