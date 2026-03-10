package com.cuecall.app.di;

import com.cuecall.app.data.local.AppDatabase;
import com.cuecall.app.data.local.dao.CallEventDao;
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
public final class DatabaseModule_ProvideCallEventDaoFactory implements Factory<CallEventDao> {
  private final Provider<AppDatabase> dbProvider;

  public DatabaseModule_ProvideCallEventDaoFactory(Provider<AppDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public CallEventDao get() {
    return provideCallEventDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideCallEventDaoFactory create(Provider<AppDatabase> dbProvider) {
    return new DatabaseModule_ProvideCallEventDaoFactory(dbProvider);
  }

  public static CallEventDao provideCallEventDao(AppDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideCallEventDao(db));
  }
}
