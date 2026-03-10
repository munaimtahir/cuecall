package com.cuecall.app.domain.usecase;

import com.cuecall.app.domain.repository.QueueDayRepository;
import com.cuecall.app.domain.repository.ServiceRepository;
import com.cuecall.app.domain.repository.SettingsRepository;
import com.cuecall.app.domain.repository.TokenSequenceRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class ResetQueueDayUseCase_Factory implements Factory<ResetQueueDayUseCase> {
  private final Provider<QueueDayRepository> queueDayRepositoryProvider;

  private final Provider<TokenSequenceRepository> tokenSequenceRepositoryProvider;

  private final Provider<ServiceRepository> serviceRepositoryProvider;

  private final Provider<SettingsRepository> settingsRepositoryProvider;

  public ResetQueueDayUseCase_Factory(Provider<QueueDayRepository> queueDayRepositoryProvider,
      Provider<TokenSequenceRepository> tokenSequenceRepositoryProvider,
      Provider<ServiceRepository> serviceRepositoryProvider,
      Provider<SettingsRepository> settingsRepositoryProvider) {
    this.queueDayRepositoryProvider = queueDayRepositoryProvider;
    this.tokenSequenceRepositoryProvider = tokenSequenceRepositoryProvider;
    this.serviceRepositoryProvider = serviceRepositoryProvider;
    this.settingsRepositoryProvider = settingsRepositoryProvider;
  }

  @Override
  public ResetQueueDayUseCase get() {
    return newInstance(queueDayRepositoryProvider.get(), tokenSequenceRepositoryProvider.get(), serviceRepositoryProvider.get(), settingsRepositoryProvider.get());
  }

  public static ResetQueueDayUseCase_Factory create(
      Provider<QueueDayRepository> queueDayRepositoryProvider,
      Provider<TokenSequenceRepository> tokenSequenceRepositoryProvider,
      Provider<ServiceRepository> serviceRepositoryProvider,
      Provider<SettingsRepository> settingsRepositoryProvider) {
    return new ResetQueueDayUseCase_Factory(queueDayRepositoryProvider, tokenSequenceRepositoryProvider, serviceRepositoryProvider, settingsRepositoryProvider);
  }

  public static ResetQueueDayUseCase newInstance(QueueDayRepository queueDayRepository,
      TokenSequenceRepository tokenSequenceRepository, ServiceRepository serviceRepository,
      SettingsRepository settingsRepository) {
    return new ResetQueueDayUseCase(queueDayRepository, tokenSequenceRepository, serviceRepository, settingsRepository);
  }
}
