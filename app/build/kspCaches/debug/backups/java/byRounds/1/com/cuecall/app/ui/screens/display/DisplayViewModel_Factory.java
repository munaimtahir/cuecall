package com.cuecall.app.ui.screens.display;

import com.cuecall.app.domain.repository.QueueDayRepository;
import com.cuecall.app.domain.repository.SettingsRepository;
import com.cuecall.app.domain.repository.TokenRepository;
import com.cuecall.app.sync.TokenSyncManager;
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
public final class DisplayViewModel_Factory implements Factory<DisplayViewModel> {
  private final Provider<TokenRepository> tokenRepositoryProvider;

  private final Provider<QueueDayRepository> queueDayRepositoryProvider;

  private final Provider<SettingsRepository> settingsRepositoryProvider;

  private final Provider<TokenSyncManager> tokenSyncManagerProvider;

  public DisplayViewModel_Factory(Provider<TokenRepository> tokenRepositoryProvider,
      Provider<QueueDayRepository> queueDayRepositoryProvider,
      Provider<SettingsRepository> settingsRepositoryProvider,
      Provider<TokenSyncManager> tokenSyncManagerProvider) {
    this.tokenRepositoryProvider = tokenRepositoryProvider;
    this.queueDayRepositoryProvider = queueDayRepositoryProvider;
    this.settingsRepositoryProvider = settingsRepositoryProvider;
    this.tokenSyncManagerProvider = tokenSyncManagerProvider;
  }

  @Override
  public DisplayViewModel get() {
    return newInstance(tokenRepositoryProvider.get(), queueDayRepositoryProvider.get(), settingsRepositoryProvider.get(), tokenSyncManagerProvider.get());
  }

  public static DisplayViewModel_Factory create(Provider<TokenRepository> tokenRepositoryProvider,
      Provider<QueueDayRepository> queueDayRepositoryProvider,
      Provider<SettingsRepository> settingsRepositoryProvider,
      Provider<TokenSyncManager> tokenSyncManagerProvider) {
    return new DisplayViewModel_Factory(tokenRepositoryProvider, queueDayRepositoryProvider, settingsRepositoryProvider, tokenSyncManagerProvider);
  }

  public static DisplayViewModel newInstance(TokenRepository tokenRepository,
      QueueDayRepository queueDayRepository, SettingsRepository settingsRepository,
      TokenSyncManager tokenSyncManager) {
    return new DisplayViewModel(tokenRepository, queueDayRepository, settingsRepository, tokenSyncManager);
  }
}
