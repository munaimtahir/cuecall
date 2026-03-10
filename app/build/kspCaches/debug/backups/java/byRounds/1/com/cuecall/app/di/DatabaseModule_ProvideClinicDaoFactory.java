package com.cuecall.app.di;

import com.cuecall.app.data.local.AppDatabase;
import com.cuecall.app.data.local.dao.ClinicDao;
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
public final class DatabaseModule_ProvideClinicDaoFactory implements Factory<ClinicDao> {
  private final Provider<AppDatabase> dbProvider;

  public DatabaseModule_ProvideClinicDaoFactory(Provider<AppDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public ClinicDao get() {
    return provideClinicDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideClinicDaoFactory create(Provider<AppDatabase> dbProvider) {
    return new DatabaseModule_ProvideClinicDaoFactory(dbProvider);
  }

  public static ClinicDao provideClinicDao(AppDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideClinicDao(db));
  }
}
