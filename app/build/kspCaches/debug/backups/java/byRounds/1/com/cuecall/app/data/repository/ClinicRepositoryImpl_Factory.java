package com.cuecall.app.data.repository;

import com.cuecall.app.data.local.dao.ClinicDao;
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
public final class ClinicRepositoryImpl_Factory implements Factory<ClinicRepositoryImpl> {
  private final Provider<ClinicDao> clinicDaoProvider;

  public ClinicRepositoryImpl_Factory(Provider<ClinicDao> clinicDaoProvider) {
    this.clinicDaoProvider = clinicDaoProvider;
  }

  @Override
  public ClinicRepositoryImpl get() {
    return newInstance(clinicDaoProvider.get());
  }

  public static ClinicRepositoryImpl_Factory create(Provider<ClinicDao> clinicDaoProvider) {
    return new ClinicRepositoryImpl_Factory(clinicDaoProvider);
  }

  public static ClinicRepositoryImpl newInstance(ClinicDao clinicDao) {
    return new ClinicRepositoryImpl(clinicDao);
  }
}
