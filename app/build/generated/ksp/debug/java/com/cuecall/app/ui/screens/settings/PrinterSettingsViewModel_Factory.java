package com.cuecall.app.ui.screens.settings;

import android.bluetooth.BluetoothAdapter;
import com.cuecall.app.domain.repository.SettingsRepository;
import com.cuecall.app.printer.PrinterManager;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
public final class PrinterSettingsViewModel_Factory implements Factory<PrinterSettingsViewModel> {
  private final Provider<SettingsRepository> settingsRepositoryProvider;

  private final Provider<PrinterManager> printerManagerProvider;

  private final Provider<BluetoothAdapter> bluetoothAdapterProvider;

  public PrinterSettingsViewModel_Factory(Provider<SettingsRepository> settingsRepositoryProvider,
      Provider<PrinterManager> printerManagerProvider,
      Provider<BluetoothAdapter> bluetoothAdapterProvider) {
    this.settingsRepositoryProvider = settingsRepositoryProvider;
    this.printerManagerProvider = printerManagerProvider;
    this.bluetoothAdapterProvider = bluetoothAdapterProvider;
  }

  @Override
  public PrinterSettingsViewModel get() {
    return newInstance(settingsRepositoryProvider.get(), printerManagerProvider.get(), bluetoothAdapterProvider.get());
  }

  public static PrinterSettingsViewModel_Factory create(
      Provider<SettingsRepository> settingsRepositoryProvider,
      Provider<PrinterManager> printerManagerProvider,
      Provider<BluetoothAdapter> bluetoothAdapterProvider) {
    return new PrinterSettingsViewModel_Factory(settingsRepositoryProvider, printerManagerProvider, bluetoothAdapterProvider);
  }

  public static PrinterSettingsViewModel newInstance(SettingsRepository settingsRepository,
      PrinterManager printerManager, BluetoothAdapter bluetoothAdapter) {
    return new PrinterSettingsViewModel(settingsRepository, printerManager, bluetoothAdapter);
  }
}
