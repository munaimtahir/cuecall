package com.cuecall.app.di;

import com.cuecall.app.data.local.AppDatabase;
import com.cuecall.app.data.local.dao.QueueDayDao;
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
public final class DatabaseModule_ProvideQueueDayDaoFactory implements Factory<QueueDayDao> {
  private final Provider<AppDatabase> dbProvider;

  public DatabaseModule_ProvideQueueDayDaoFactory(Provider<AppDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public QueueDayDao get() {
    return provideQueueDayDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideQueueDayDaoFactory create(Provider<AppDatabase> dbProvider) {
    return new DatabaseModule_ProvideQueueDayDaoFactory(dbProvider);
  }

  public static QueueDayDao provideQueueDayDao(AppDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideQueueDayDao(db));
  }
}
