package com.cuecall.app.di;

import com.cuecall.app.data.local.AppDatabase;
import com.cuecall.app.data.local.dao.DeviceDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class DatabaseModule_ProvideDeviceDaoFactory implements Factory<DeviceDao> {
  private final Provider<AppDatabase> dbProvider;

  public DatabaseModule_ProvideDeviceDaoFactory(Provider<AppDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public DeviceDao get() {
    return provideDeviceDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideDeviceDaoFactory create(Provider<AppDatabase> dbProvider) {
    return new DatabaseModule_ProvideDeviceDaoFactory(dbProvider);
  }

  public static DeviceDao provideDeviceDao(AppDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideDeviceDao(db));
  }
}
