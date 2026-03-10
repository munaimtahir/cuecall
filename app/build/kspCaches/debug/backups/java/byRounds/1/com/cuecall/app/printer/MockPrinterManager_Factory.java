package com.cuecall.app.printer;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

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
public final class MockPrinterManager_Factory implements Factory<MockPrinterManager> {
  @Override
  public MockPrinterManager get() {
    return newInstance();
  }

  public static MockPrinterManager_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static MockPrinterManager newInstance() {
    return new MockPrinterManager();
  }

  private static final class InstanceHolder {
    private static final MockPrinterManager_Factory INSTANCE = new MockPrinterManager_Factory();
  }
}
