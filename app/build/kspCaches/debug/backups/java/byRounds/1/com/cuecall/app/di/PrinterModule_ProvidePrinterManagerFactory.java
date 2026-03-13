package com.cuecall.app.di;

import com.cuecall.app.printer.EscPosPrinterManager;
import com.cuecall.app.printer.PrinterManager;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast"
})
public final class PrinterModule_ProvidePrinterManagerFactory implements Factory<PrinterManager> {
  private final Provider<EscPosPrinterManager> escPosPrinterManagerProvider;

  public PrinterModule_ProvidePrinterManagerFactory(
      Provider<EscPosPrinterManager> escPosPrinterManagerProvider) {
    this.escPosPrinterManagerProvider = escPosPrinterManagerProvider;
  }

  @Override
  public PrinterManager get() {
    return providePrinterManager(escPosPrinterManagerProvider.get());
  }

  public static PrinterModule_ProvidePrinterManagerFactory create(
      Provider<EscPosPrinterManager> escPosPrinterManagerProvider) {
    return new PrinterModule_ProvidePrinterManagerFactory(escPosPrinterManagerProvider);
  }

  public static PrinterManager providePrinterManager(EscPosPrinterManager escPosPrinterManager) {
    return Preconditions.checkNotNullFromProvides(PrinterModule.INSTANCE.providePrinterManager(escPosPrinterManager));
  }
}
