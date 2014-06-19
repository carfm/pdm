package sv.ues.eisi.fia.bibliotecaeisi;

import java.io.FileOutputStream;
import java.io.IOException;

import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentInfo;
import android.print.PrintManager;
import android.print.pdf.PrintedPdfDocument;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.graphics.pdf.PdfDocument.PageInfo;
import android.view.Menu;
import android.view.View;

public class ImprimirHojaPrestamoActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_imprimir_hoja_prestamo);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.imprimir_hoja_prestamo, menu);
		return true;
	}
	
	@SuppressLint({ "NewApi", "InlinedApi" })
	public void printDocument(View view)
	{
	    PrintManager printManager = (PrintManager) this
	            .getSystemService(Context.PRINT_SERVICE);

	    String jobName = this.getString(R.string.app_name) + 
                        " Document";

	    printManager.print(jobName, new MyPrintDocumentAdapter(this),
	             null);
	}

	@SuppressLint("NewApi")
	public class MyPrintDocumentAdapter extends PrintDocumentAdapter {
		Context context;
		private int pageHeight;
		private int pageWidth;
		public PdfDocument myPdfDocument;
		public int totalpages = 4;

		public MyPrintDocumentAdapter(Context context) {
			this.context = context;
		}

		@Override
		public void onLayout(PrintAttributes oldAttributes,
				PrintAttributes newAttributes,
				CancellationSignal cancellationSignal,
				LayoutResultCallback callback, Bundle metadata) {

			myPdfDocument = new PrintedPdfDocument(context, newAttributes);
			// medidas de las paginas
			pageHeight = newAttributes.getMediaSize().getHeightMils() / 1000 * 72;
			pageWidth = newAttributes.getMediaSize().getWidthMils() / 1000 * 72;

			if (cancellationSignal.isCanceled()) {
				callback.onLayoutCancelled();
				return;
			}

			if (totalpages > 0) {
				PrintDocumentInfo.Builder builder = new PrintDocumentInfo.Builder(
						"print_output.pdf").setContentType(
						PrintDocumentInfo.CONTENT_TYPE_DOCUMENT).setPageCount(
						totalpages);

				PrintDocumentInfo info = builder.build();
				callback.onLayoutFinished(info, true);
			} else {
				callback.onLayoutFailed("Page count is zero.");
			}
		}

		@Override
		public void onWrite(final PageRange[] pageRanges,
				final ParcelFileDescriptor destination,
				final CancellationSignal cancellationSignal,
				final WriteResultCallback callback) {

			for (int i = 0; i < totalpages; i++) {
				if (pageInRange(pageRanges, i)) {
					PageInfo newPage = new PageInfo.Builder(pageWidth,
							pageHeight, i).create();

					PdfDocument.Page page = myPdfDocument.startPage(newPage);

					if (cancellationSignal.isCanceled()) {
						callback.onWriteCancelled();
						myPdfDocument.close();
						myPdfDocument = null;
						return;
					}
					drawPage(page, i);
					myPdfDocument.finishPage(page);
				}
			}

			try {
				myPdfDocument.writeTo(new FileOutputStream(destination
						.getFileDescriptor()));
			} catch (IOException e) {
				callback.onWriteFailed(e.toString());
				return;
			} finally {
				myPdfDocument.close();
				myPdfDocument = null;
			}

			callback.onWriteFinished(pageRanges);
		}

		private boolean pageInRange(PageRange[] pageRanges, int page) {
			for (int i = 0; i < pageRanges.length; i++) {
				if ((page >= pageRanges[i].getStart())
						&& (page <= pageRanges[i].getEnd()))
					return true;
			}
			return false;
		}

		private void drawPage(PdfDocument.Page page, int pagenumber) {
			Canvas canvas = page.getCanvas();

			pagenumber++; // Make sure page numbers start at 1

			int titleBaseLine = 72;
			int leftMargin = 54;

			Paint paint = new Paint();
			paint.setColor(Color.BLACK);
			paint.setTextSize(40);
			canvas.drawText("Test Print Document Page " + pagenumber,
					leftMargin, titleBaseLine, paint);

			paint.setTextSize(14);
			canvas.drawText(
					"This is some test content to verify that custom document printing works",
					leftMargin, titleBaseLine + 35, paint);

			if (pagenumber % 2 == 0)
				paint.setColor(Color.RED);
			else
				paint.setColor(Color.GREEN);

			PageInfo pageInfo = page.getInfo();

			canvas.drawCircle(pageInfo.getPageWidth() / 2,
					pageInfo.getPageHeight() / 2, 150, paint);
		}
	}
}
