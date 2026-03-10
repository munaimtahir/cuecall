package com.cuecall.app.ui.screens.history;

import com.cuecall.app.domain.repository.QueueDayRepository;
import com.cuecall.app.domain.repository.ServiceRepository;
import com.cuecall.app.domain.repository.SettingsRepository;
import com.cuecall.app.domain.repository.TokenRepository;
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
public final class DailyHistoryViewModel_Factory implements Factory<DailyHistoryViewModel> {
  private final Provider<TokenRepository> tokenRepositoryProvider;

  private final Provider<QueueDayRepository> queueDayRepositoryProvider;

  private final Provider<SettingsRepository> settingsRepositoryProvider;

  private final Provider<ServiceRepository> serviceRepositoryProvider;

  public DailyHistoryViewModel_Factory(Provider<TokenRepository> tokenRepositoryProvider,
      Provider<QueueDayRepository> queueDayRepositoryProvider,
      Provider<SettingsRepository> settingsRepositoryProvider,
      Provider<ServiceRepository> serviceRepositoryProvider) {
    this.tokenRepositoryProvider = tokenRepositoryProvider;
    this.queueDayRepositoryProvider = queueDayRepositoryProvider;
    this.settingsRepositoryProvider = settingsRepositoryProvider;
    this.serviceRepositoryProvider = serviceRepositoryProvider;
  }

  @Override
  public DailyHistoryViewModel get() {
    return newInstance(tokenRepositoryProvider.get(), queueDayRepositoryProvider.get(), settingsRepositoryProvider.get(), serviceRepositoryProvider.get());
  }

  public static DailyHistoryViewModel_Factory create(
      Provider<TokenRepository> tokenRepositoryProvider,
      Provider<QueueDayRepository> queueDayRepositoryProvider,
      Provider<SettingsRepository> settingsRepositoryProvider,
      Provider<ServiceRepository> serviceRepositoryProvider) {
    return new DailyHistoryViewModel_Factory(tokenRepositoryProvider, queueDayRepositoryProvider, settingsRepositoryProvider, serviceRepositoryProvider);
  }

  public static DailyHistoryViewModel newInstance(TokenRepository tokenRepository,
      QueueDayRepository queueDayRepository, SettingsRepository settingsRepository,
      ServiceRepository serviceRepository) {
    return new DailyHistoryViewModel(tokenRepository, queueDayRepository, settingsRepository, serviceRepository);
  }
}
