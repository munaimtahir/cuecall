package com.cuecall.app.data.repository;

import com.cuecall.app.data.local.dao.QueueDayDao;
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
public final class QueueDayRepositoryImpl_Factory implements Factory<QueueDayRepositoryImpl> {
  private final Provider<QueueDayDao> queueDayDaoProvider;

  public QueueDayRepositoryImpl_Factory(Provider<QueueDayDao> queueDayDaoProvider) {
    this.queueDayDaoProvider = queueDayDaoProvider;
  }

  @Override
  public QueueDayRepositoryImpl get() {
    return newInstance(queueDayDaoProvider.get());
  }

  public static QueueDayRepositoryImpl_Factory create(Provider<QueueDayDao> queueDayDaoProvider) {
    return new QueueDayRepositoryImpl_Factory(queueDayDaoProvider);
  }

  public static QueueDayRepositoryImpl newInstance(QueueDayDao queueDayDao) {
    return new QueueDayRepositoryImpl(queueDayDao);
  }
}
