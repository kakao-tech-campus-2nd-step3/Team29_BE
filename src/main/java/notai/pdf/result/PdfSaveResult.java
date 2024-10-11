package notai.pdf.result;

import java.io.File;

public record PdfSaveResult(
        String pdfName,
        String pdfUrl,
        File pdf
) {
    public static PdfSaveResult of(
            String pdfName, File pdf
    ) {
        return new PdfSaveResult(pdfName, convertPdfUrl(pdfName), pdf);
    }

    private static String convertPdfUrl(String pdfName) {
        return String.format("pdf/%s", pdfName);
    }
}
