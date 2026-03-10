package com.cuecall.app.data.repository;

import com.cuecall.app.data.local.dao.CallEventDao;
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
public final class CallEventRepositoryImpl_Factory implements Factory<CallEventRepositoryImpl> {
  private final Provider<CallEventDao> callEventDaoProvider;

  public CallEventRepositoryImpl_Factory(Provider<CallEventDao> callEventDaoProvider) {
    this.callEventDaoProvider = callEventDaoProvider;
  }

  @Override
  public CallEventRepositoryImpl get() {
    return newInstance(callEventDaoProvider.get());
  }

  public static CallEventRepositoryImpl_Factory create(
      Provider<CallEventDao> callEventDaoProvider) {
    return new CallEventRepositoryImpl_Factory(callEventDaoProvider);
  }

  public static CallEventRepositoryImpl newInstance(CallEventDao callEventDao) {
    return new CallEventRepositoryImpl(callEventDao);
  }
}
