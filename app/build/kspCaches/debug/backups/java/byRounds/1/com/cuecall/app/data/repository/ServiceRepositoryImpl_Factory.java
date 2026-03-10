package com.cuecall.app.data.repository;

import com.cuecall.app.data.local.dao.ServiceDao;
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
public final class ServiceRepositoryImpl_Factory implements Factory<ServiceRepositoryImpl> {
  private final Provider<ServiceDao> serviceDaoProvider;

  public ServiceRepositoryImpl_Factory(Provider<ServiceDao> serviceDaoProvider) {
    this.serviceDaoProvider = serviceDaoProvider;
  }

  @Override
  public ServiceRepositoryImpl get() {
    return newInstance(serviceDaoProvider.get());
  }

  public static ServiceRepositoryImpl_Factory create(Provider<ServiceDao> serviceDaoProvider) {
    return new ServiceRepositoryImpl_Factory(serviceDaoProvider);
  }

  public static ServiceRepositoryImpl newInstance(ServiceDao serviceDao) {
    return new ServiceRepositoryImpl(serviceDao);
  }
}
