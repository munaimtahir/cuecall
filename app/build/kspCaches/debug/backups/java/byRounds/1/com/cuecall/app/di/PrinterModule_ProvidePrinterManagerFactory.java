package com.cuecall.app.di;

import com.cuecall.app.printer.EscPosPrinterManager;
import com.cuecall.app.printer.MockPrinterManager;
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
  private final Provider<MockPrinterManager> mockPrinterManagerProvider;

  private final Provider<EscPosPrinterManager> escPosPrinterManagerProvider;

  public PrinterModule_ProvidePrinterManagerFactory(
      Provider<MockPrinterManager> mockPrinterManagerProvider,
      Provider<EscPosPrinterManager> escPosPrinterManagerProvider) {
    this.mockPrinterManagerProvider = mockPrinterManagerProvider;
    this.escPosPrinterManagerProvider = escPosPrinterManagerProvider;
  }

  @Override
  public PrinterManager get() {
    return providePrinterManager(mockPrinterManagerProvider.get(), escPosPrinterManagerProvider.get());
  }

  public static PrinterModule_ProvidePrinterManagerFactory create(
      Provider<MockPrinterManager> mockPrinterManagerProvider,
      Provider<EscPosPrinterManager> escPosPrinterManagerProvider) {
    return new PrinterModule_ProvidePrinterManagerFactory(mockPrinterManagerProvider, escPosPrinterManagerProvider);
  }

  public static PrinterManager providePrinterManager(MockPrinterManager mockPrinterManager,
      EscPosPrinterManager escPosPrinterManager) {
    return Preconditions.checkNotNullFromProvides(PrinterModule.INSTANCE.providePrinterManager(mockPrinterManager, escPosPrinterManager));
  }
}
