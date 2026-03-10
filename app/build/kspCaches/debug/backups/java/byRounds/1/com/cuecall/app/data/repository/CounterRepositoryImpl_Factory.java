package com.cuecall.app.data.repository;

import com.cuecall.app.data.local.dao.CounterDao;
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
public final class CounterRepositoryImpl_Factory implements Factory<CounterRepositoryImpl> {
  private final Provider<CounterDao> counterDaoProvider;

  public CounterRepositoryImpl_Factory(Provider<CounterDao> counterDaoProvider) {
    this.counterDaoProvider = counterDaoProvider;
  }

  @Override
  public CounterRepositoryImpl get() {
    return newInstance(counterDaoProvider.get());
  }

  public static CounterRepositoryImpl_Factory create(Provider<CounterDao> counterDaoProvider) {
    return new CounterRepositoryImpl_Factory(counterDaoProvider);
  }

  public static CounterRepositoryImpl newInstance(CounterDao counterDao) {
    return new CounterRepositoryImpl(counterDao);
  }
}
