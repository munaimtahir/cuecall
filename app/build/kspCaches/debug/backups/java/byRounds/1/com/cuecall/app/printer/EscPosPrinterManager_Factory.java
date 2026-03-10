package com.cuecall.app.printer;

import android.bluetooth.BluetoothAdapter;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class EscPosPrinterManager_Factory implements Factory<EscPosPrinterManager> {
  private final Provider<BluetoothAdapter> bluetoothAdapterProvider;

  public EscPosPrinterManager_Factory(Provider<BluetoothAdapter> bluetoothAdapterProvider) {
    this.bluetoothAdapterProvider = bluetoothAdapterProvider;
  }

  @Override
  public EscPosPrinterManager get() {
    return newInstance(bluetoothAdapterProvider.get());
  }

  public static EscPosPrinterManager_Factory create(
      Provider<BluetoothAdapter> bluetoothAdapterProvider) {
    return new EscPosPrinterManager_Factory(bluetoothAdapterProvider);
  }

  public static EscPosPrinterManager newInstance(BluetoothAdapter bluetoothAdapter) {
    return new EscPosPrinterManager(bluetoothAdapter);
  }
}
