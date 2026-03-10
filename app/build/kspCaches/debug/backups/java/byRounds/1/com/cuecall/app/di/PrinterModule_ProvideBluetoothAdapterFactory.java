package com.cuecall.app.di;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class PrinterModule_ProvideBluetoothAdapterFactory implements Factory<BluetoothAdapter> {
  private final Provider<Context> contextProvider;

  public PrinterModule_ProvideBluetoothAdapterFactory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public BluetoothAdapter get() {
    return provideBluetoothAdapter(contextProvider.get());
  }

  public static PrinterModule_ProvideBluetoothAdapterFactory create(
      Provider<Context> contextProvider) {
    return new PrinterModule_ProvideBluetoothAdapterFactory(contextProvider);
  }

  public static BluetoothAdapter provideBluetoothAdapter(Context context) {
    return PrinterModule.INSTANCE.provideBluetoothAdapter(context);
  }
}
