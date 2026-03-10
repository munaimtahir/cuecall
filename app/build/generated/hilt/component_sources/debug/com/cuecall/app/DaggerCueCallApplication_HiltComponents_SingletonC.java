package com.cuecall.app;

import android.app.Activity;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.view.View;
import androidx.datastore.core.DataStore;
import androidx.datastore.preferences.core.Preferences;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import com.cuecall.app.data.local.AppDatabase;
import com.cuecall.app.data.local.dao.CallEventDao;
import com.cuecall.app.data.local.dao.CounterDao;
import com.cuecall.app.data.local.dao.QueueDayDao;
import com.cuecall.app.data.local.dao.ServiceDao;
import com.cuecall.app.data.local.dao.TokenDao;
import com.cuecall.app.data.remote.source.FirestoreTokenSource;
import com.cuecall.app.data.repository.CallEventRepositoryImpl;
import com.cuecall.app.data.repository.CounterRepositoryImpl;
import com.cuecall.app.data.repository.QueueDayRepositoryImpl;
import com.cuecall.app.data.repository.ServiceRepositoryImpl;
import com.cuecall.app.data.repository.SettingsRepositoryImpl;
import com.cuecall.app.data.repository.TokenRepositoryImpl;
import com.cuecall.app.data.repository.TokenSequenceRepositoryImpl;
import com.cuecall.app.di.DatabaseModule_ProvideCallEventDaoFactory;
import com.cuecall.app.di.DatabaseModule_ProvideCounterDaoFactory;
import com.cuecall.app.di.DatabaseModule_ProvideDataStoreFactory;
import com.cuecall.app.di.DatabaseModule_ProvideDatabaseFactory;
import com.cuecall.app.di.DatabaseModule_ProvideQueueDayDaoFactory;
import com.cuecall.app.di.DatabaseModule_ProvideServiceDaoFactory;
import com.cuecall.app.di.DatabaseModule_ProvideTokenDaoFactory;
import com.cuecall.app.di.FirestoreModule_ProvideFirestoreFactory;
import com.cuecall.app.di.PrinterModule;
import com.cuecall.app.di.PrinterModule_ProvidePrinterManagerFactory;
import com.cuecall.app.domain.usecase.CallNextTokenUseCase;
import com.cuecall.app.domain.usecase.CompleteTokenUseCase;
import com.cuecall.app.domain.usecase.GenerateTokenUseCase;
import com.cuecall.app.domain.usecase.RecallTokenUseCase;
import com.cuecall.app.domain.usecase.SkipTokenUseCase;
import com.cuecall.app.printer.EscPosPrinterManager;
import com.cuecall.app.printer.MockPrinterManager;
import com.cuecall.app.printer.PrinterManager;
import com.cuecall.app.sync.TokenSyncManager;
import com.cuecall.app.ui.screens.counter.CounterViewModel;
import com.cuecall.app.ui.screens.counter.CounterViewModel_HiltModules;
import com.cuecall.app.ui.screens.display.DisplayViewModel;
import com.cuecall.app.ui.screens.display.DisplayViewModel_HiltModules;
import com.cuecall.app.ui.screens.history.DailyHistoryViewModel;
import com.cuecall.app.ui.screens.history.DailyHistoryViewModel_HiltModules;
import com.cuecall.app.ui.screens.reception.ReceptionViewModel;
import com.cuecall.app.ui.screens.reception.ReceptionViewModel_HiltModules;
import com.cuecall.app.ui.screens.settings.CounterManagementViewModel;
import com.cuecall.app.ui.screens.settings.CounterManagementViewModel_HiltModules;
import com.cuecall.app.ui.screens.settings.PrinterSettingsViewModel;
import com.cuecall.app.ui.screens.settings.PrinterSettingsViewModel_HiltModules;
import com.cuecall.app.ui.screens.settings.ServiceManagementViewModel;
import com.cuecall.app.ui.screens.settings.ServiceManagementViewModel_HiltModules;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.firebase.firestore.FirebaseFirestore;
import dagger.hilt.android.ActivityRetainedLifecycle;
import dagger.hilt.android.ViewModelLifecycle;
import dagger.hilt.android.internal.builders.ActivityComponentBuilder;
import dagger.hilt.android.internal.builders.ActivityRetainedComponentBuilder;
import dagger.hilt.android.internal.builders.FragmentComponentBuilder;
import dagger.hilt.android.internal.builders.ServiceComponentBuilder;
import dagger.hilt.android.internal.builders.ViewComponentBuilder;
import dagger.hilt.android.internal.builders.ViewModelComponentBuilder;
import dagger.hilt.android.internal.builders.ViewWithFragmentComponentBuilder;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories_InternalFactoryFactory_Factory;
import dagger.hilt.android.internal.managers.ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory;
import dagger.hilt.android.internal.managers.SavedStateHandleHolder;
import dagger.hilt.android.internal.modules.ApplicationContextModule;
import dagger.hilt.android.internal.modules.ApplicationContextModule_ProvideContextFactory;
import dagger.internal.DaggerGenerated;
import dagger.internal.DoubleCheck;
import dagger.internal.IdentifierNameString;
import dagger.internal.KeepFieldType;
import dagger.internal.LazyClassKeyMap;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

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
public final class DaggerCueCallApplication_HiltComponents_SingletonC {
  private DaggerCueCallApplication_HiltComponents_SingletonC() {
  }

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    private ApplicationContextModule applicationContextModule;

    private Builder() {
    }

    public Builder applicationContextModule(ApplicationContextModule applicationContextModule) {
      this.applicationContextModule = Preconditions.checkNotNull(applicationContextModule);
      return this;
    }

    public CueCallApplication_HiltComponents.SingletonC build() {
      Preconditions.checkBuilderRequirement(applicationContextModule, ApplicationContextModule.class);
      return new SingletonCImpl(applicationContextModule);
    }
  }

  private static final class ActivityRetainedCBuilder implements CueCallApplication_HiltComponents.ActivityRetainedC.Builder {
    private final SingletonCImpl singletonCImpl;

    private SavedStateHandleHolder savedStateHandleHolder;

    private ActivityRetainedCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ActivityRetainedCBuilder savedStateHandleHolder(
        SavedStateHandleHolder savedStateHandleHolder) {
      this.savedStateHandleHolder = Preconditions.checkNotNull(savedStateHandleHolder);
      return this;
    }

    @Override
    public CueCallApplication_HiltComponents.ActivityRetainedC build() {
      Preconditions.checkBuilderRequirement(savedStateHandleHolder, SavedStateHandleHolder.class);
      return new ActivityRetainedCImpl(singletonCImpl, savedStateHandleHolder);
    }
  }

  private static final class ActivityCBuilder implements CueCallApplication_HiltComponents.ActivityC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private Activity activity;

    private ActivityCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ActivityCBuilder activity(Activity activity) {
      this.activity = Preconditions.checkNotNull(activity);
      return this;
    }

    @Override
    public CueCallApplication_HiltComponents.ActivityC build() {
      Preconditions.checkBuilderRequirement(activity, Activity.class);
      return new ActivityCImpl(singletonCImpl, activityRetainedCImpl, activity);
    }
  }

  private static final class FragmentCBuilder implements CueCallApplication_HiltComponents.FragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private Fragment fragment;

    private FragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public FragmentCBuilder fragment(Fragment fragment) {
      this.fragment = Preconditions.checkNotNull(fragment);
      return this;
    }

    @Override
    public CueCallApplication_HiltComponents.FragmentC build() {
      Preconditions.checkBuilderRequirement(fragment, Fragment.class);
      return new FragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragment);
    }
  }

  private static final class ViewWithFragmentCBuilder implements CueCallApplication_HiltComponents.ViewWithFragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private View view;

    private ViewWithFragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;
    }

    @Override
    public ViewWithFragmentCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public CueCallApplication_HiltComponents.ViewWithFragmentC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewWithFragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl, view);
    }
  }

  private static final class ViewCBuilder implements CueCallApplication_HiltComponents.ViewC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private View view;

    private ViewCBuilder(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public ViewCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public CueCallApplication_HiltComponents.ViewC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, view);
    }
  }

  private static final class ViewModelCBuilder implements CueCallApplication_HiltComponents.ViewModelC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private SavedStateHandle savedStateHandle;

    private ViewModelLifecycle viewModelLifecycle;

    private ViewModelCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ViewModelCBuilder savedStateHandle(SavedStateHandle handle) {
      this.savedStateHandle = Preconditions.checkNotNull(handle);
      return this;
    }

    @Override
    public ViewModelCBuilder viewModelLifecycle(ViewModelLifecycle viewModelLifecycle) {
      this.viewModelLifecycle = Preconditions.checkNotNull(viewModelLifecycle);
      return this;
    }

    @Override
    public CueCallApplication_HiltComponents.ViewModelC build() {
      Preconditions.checkBuilderRequirement(savedStateHandle, SavedStateHandle.class);
      Preconditions.checkBuilderRequirement(viewModelLifecycle, ViewModelLifecycle.class);
      return new ViewModelCImpl(singletonCImpl, activityRetainedCImpl, savedStateHandle, viewModelLifecycle);
    }
  }

  private static final class ServiceCBuilder implements CueCallApplication_HiltComponents.ServiceC.Builder {
    private final SingletonCImpl singletonCImpl;

    private Service service;

    private ServiceCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ServiceCBuilder service(Service service) {
      this.service = Preconditions.checkNotNull(service);
      return this;
    }

    @Override
    public CueCallApplication_HiltComponents.ServiceC build() {
      Preconditions.checkBuilderRequirement(service, Service.class);
      return new ServiceCImpl(singletonCImpl, service);
    }
  }

  private static final class ViewWithFragmentCImpl extends CueCallApplication_HiltComponents.ViewWithFragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private final ViewWithFragmentCImpl viewWithFragmentCImpl = this;

    private ViewWithFragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;


    }
  }

  private static final class FragmentCImpl extends CueCallApplication_HiltComponents.FragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl = this;

    private FragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        Fragment fragmentParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return activityCImpl.getHiltInternalFactoryFactory();
    }

    @Override
    public ViewWithFragmentComponentBuilder viewWithFragmentComponentBuilder() {
      return new ViewWithFragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl);
    }
  }

  private static final class ViewCImpl extends CueCallApplication_HiltComponents.ViewC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final ViewCImpl viewCImpl = this;

    private ViewCImpl(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }
  }

  private static final class ActivityCImpl extends CueCallApplication_HiltComponents.ActivityC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl = this;

    private ActivityCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, Activity activityParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;


    }

    @Override
    public void injectMainActivity(MainActivity mainActivity) {
    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return DefaultViewModelFactories_InternalFactoryFactory_Factory.newInstance(getViewModelKeys(), new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl));
    }

    @Override
    public Map<Class<?>, Boolean> getViewModelKeys() {
      return LazyClassKeyMap.<Boolean>of(ImmutableMap.<String, Boolean>builderWithExpectedSize(7).put(LazyClassKeyProvider.com_cuecall_app_ui_screens_settings_CounterManagementViewModel, CounterManagementViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_cuecall_app_ui_screens_counter_CounterViewModel, CounterViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_cuecall_app_ui_screens_history_DailyHistoryViewModel, DailyHistoryViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_cuecall_app_ui_screens_display_DisplayViewModel, DisplayViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_cuecall_app_ui_screens_settings_PrinterSettingsViewModel, PrinterSettingsViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_cuecall_app_ui_screens_reception_ReceptionViewModel, ReceptionViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_cuecall_app_ui_screens_settings_ServiceManagementViewModel, ServiceManagementViewModel_HiltModules.KeyModule.provide()).build());
    }

    @Override
    public ViewModelComponentBuilder getViewModelComponentBuilder() {
      return new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public FragmentComponentBuilder fragmentComponentBuilder() {
      return new FragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    @Override
    public ViewComponentBuilder viewComponentBuilder() {
      return new ViewCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    @IdentifierNameString
    private static final class LazyClassKeyProvider {
      static String com_cuecall_app_ui_screens_display_DisplayViewModel = "com.cuecall.app.ui.screens.display.DisplayViewModel";

      static String com_cuecall_app_ui_screens_counter_CounterViewModel = "com.cuecall.app.ui.screens.counter.CounterViewModel";

      static String com_cuecall_app_ui_screens_settings_PrinterSettingsViewModel = "com.cuecall.app.ui.screens.settings.PrinterSettingsViewModel";

      static String com_cuecall_app_ui_screens_settings_ServiceManagementViewModel = "com.cuecall.app.ui.screens.settings.ServiceManagementViewModel";

      static String com_cuecall_app_ui_screens_history_DailyHistoryViewModel = "com.cuecall.app.ui.screens.history.DailyHistoryViewModel";

      static String com_cuecall_app_ui_screens_settings_CounterManagementViewModel = "com.cuecall.app.ui.screens.settings.CounterManagementViewModel";

      static String com_cuecall_app_ui_screens_reception_ReceptionViewModel = "com.cuecall.app.ui.screens.reception.ReceptionViewModel";

      @KeepFieldType
      DisplayViewModel com_cuecall_app_ui_screens_display_DisplayViewModel2;

      @KeepFieldType
      CounterViewModel com_cuecall_app_ui_screens_counter_CounterViewModel2;

      @KeepFieldType
      PrinterSettingsViewModel com_cuecall_app_ui_screens_settings_PrinterSettingsViewModel2;

      @KeepFieldType
      ServiceManagementViewModel com_cuecall_app_ui_screens_settings_ServiceManagementViewModel2;

      @KeepFieldType
      DailyHistoryViewModel com_cuecall_app_ui_screens_history_DailyHistoryViewModel2;

      @KeepFieldType
      CounterManagementViewModel com_cuecall_app_ui_screens_settings_CounterManagementViewModel2;

      @KeepFieldType
      ReceptionViewModel com_cuecall_app_ui_screens_reception_ReceptionViewModel2;
    }
  }

  private static final class ViewModelCImpl extends CueCallApplication_HiltComponents.ViewModelC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ViewModelCImpl viewModelCImpl = this;

    private Provider<CounterManagementViewModel> counterManagementViewModelProvider;

    private Provider<CounterViewModel> counterViewModelProvider;

    private Provider<DailyHistoryViewModel> dailyHistoryViewModelProvider;

    private Provider<DisplayViewModel> displayViewModelProvider;

    private Provider<PrinterSettingsViewModel> printerSettingsViewModelProvider;

    private Provider<ReceptionViewModel> receptionViewModelProvider;

    private Provider<ServiceManagementViewModel> serviceManagementViewModelProvider;

    private ViewModelCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, SavedStateHandle savedStateHandleParam,
        ViewModelLifecycle viewModelLifecycleParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;

      initialize(savedStateHandleParam, viewModelLifecycleParam);

    }

    private CallNextTokenUseCase callNextTokenUseCase() {
      return new CallNextTokenUseCase(singletonCImpl.tokenRepositoryImplProvider.get(), singletonCImpl.settingsRepositoryImplProvider.get(), singletonCImpl.callEventRepositoryImplProvider.get(), singletonCImpl.queueDayRepositoryImplProvider.get());
    }

    private RecallTokenUseCase recallTokenUseCase() {
      return new RecallTokenUseCase(singletonCImpl.tokenRepositoryImplProvider.get(), singletonCImpl.settingsRepositoryImplProvider.get(), singletonCImpl.callEventRepositoryImplProvider.get());
    }

    private SkipTokenUseCase skipTokenUseCase() {
      return new SkipTokenUseCase(singletonCImpl.tokenRepositoryImplProvider.get(), singletonCImpl.settingsRepositoryImplProvider.get(), singletonCImpl.callEventRepositoryImplProvider.get());
    }

    private CompleteTokenUseCase completeTokenUseCase() {
      return new CompleteTokenUseCase(singletonCImpl.tokenRepositoryImplProvider.get(), singletonCImpl.settingsRepositoryImplProvider.get(), singletonCImpl.callEventRepositoryImplProvider.get());
    }

    private GenerateTokenUseCase generateTokenUseCase() {
      return new GenerateTokenUseCase(singletonCImpl.tokenRepositoryImplProvider.get(), singletonCImpl.tokenSequenceRepositoryImplProvider.get(), singletonCImpl.queueDayRepositoryImplProvider.get(), singletonCImpl.serviceRepositoryImplProvider.get(), singletonCImpl.settingsRepositoryImplProvider.get(), singletonCImpl.callEventRepositoryImplProvider.get());
    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandle savedStateHandleParam,
        final ViewModelLifecycle viewModelLifecycleParam) {
      this.counterManagementViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 0);
      this.counterViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 1);
      this.dailyHistoryViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 2);
      this.displayViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 3);
      this.printerSettingsViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 4);
      this.receptionViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 5);
      this.serviceManagementViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 6);
    }

    @Override
    public Map<Class<?>, javax.inject.Provider<ViewModel>> getHiltViewModelMap() {
      return LazyClassKeyMap.<javax.inject.Provider<ViewModel>>of(ImmutableMap.<String, javax.inject.Provider<ViewModel>>builderWithExpectedSize(7).put(LazyClassKeyProvider.com_cuecall_app_ui_screens_settings_CounterManagementViewModel, ((Provider) counterManagementViewModelProvider)).put(LazyClassKeyProvider.com_cuecall_app_ui_screens_counter_CounterViewModel, ((Provider) counterViewModelProvider)).put(LazyClassKeyProvider.com_cuecall_app_ui_screens_history_DailyHistoryViewModel, ((Provider) dailyHistoryViewModelProvider)).put(LazyClassKeyProvider.com_cuecall_app_ui_screens_display_DisplayViewModel, ((Provider) displayViewModelProvider)).put(LazyClassKeyProvider.com_cuecall_app_ui_screens_settings_PrinterSettingsViewModel, ((Provider) printerSettingsViewModelProvider)).put(LazyClassKeyProvider.com_cuecall_app_ui_screens_reception_ReceptionViewModel, ((Provider) receptionViewModelProvider)).put(LazyClassKeyProvider.com_cuecall_app_ui_screens_settings_ServiceManagementViewModel, ((Provider) serviceManagementViewModelProvider)).build());
    }

    @Override
    public Map<Class<?>, Object> getHiltViewModelAssistedMap() {
      return ImmutableMap.<Class<?>, Object>of();
    }

    @IdentifierNameString
    private static final class LazyClassKeyProvider {
      static String com_cuecall_app_ui_screens_history_DailyHistoryViewModel = "com.cuecall.app.ui.screens.history.DailyHistoryViewModel";

      static String com_cuecall_app_ui_screens_settings_CounterManagementViewModel = "com.cuecall.app.ui.screens.settings.CounterManagementViewModel";

      static String com_cuecall_app_ui_screens_counter_CounterViewModel = "com.cuecall.app.ui.screens.counter.CounterViewModel";

      static String com_cuecall_app_ui_screens_reception_ReceptionViewModel = "com.cuecall.app.ui.screens.reception.ReceptionViewModel";

      static String com_cuecall_app_ui_screens_display_DisplayViewModel = "com.cuecall.app.ui.screens.display.DisplayViewModel";

      static String com_cuecall_app_ui_screens_settings_PrinterSettingsViewModel = "com.cuecall.app.ui.screens.settings.PrinterSettingsViewModel";

      static String com_cuecall_app_ui_screens_settings_ServiceManagementViewModel = "com.cuecall.app.ui.screens.settings.ServiceManagementViewModel";

      @KeepFieldType
      DailyHistoryViewModel com_cuecall_app_ui_screens_history_DailyHistoryViewModel2;

      @KeepFieldType
      CounterManagementViewModel com_cuecall_app_ui_screens_settings_CounterManagementViewModel2;

      @KeepFieldType
      CounterViewModel com_cuecall_app_ui_screens_counter_CounterViewModel2;

      @KeepFieldType
      ReceptionViewModel com_cuecall_app_ui_screens_reception_ReceptionViewModel2;

      @KeepFieldType
      DisplayViewModel com_cuecall_app_ui_screens_display_DisplayViewModel2;

      @KeepFieldType
      PrinterSettingsViewModel com_cuecall_app_ui_screens_settings_PrinterSettingsViewModel2;

      @KeepFieldType
      ServiceManagementViewModel com_cuecall_app_ui_screens_settings_ServiceManagementViewModel2;
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final ViewModelCImpl viewModelCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          ViewModelCImpl viewModelCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.viewModelCImpl = viewModelCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.cuecall.app.ui.screens.settings.CounterManagementViewModel 
          return (T) new CounterManagementViewModel(singletonCImpl.counterRepositoryImplProvider.get(), singletonCImpl.settingsRepositoryImplProvider.get());

          case 1: // com.cuecall.app.ui.screens.counter.CounterViewModel 
          return (T) new CounterViewModel(singletonCImpl.tokenRepositoryImplProvider.get(), singletonCImpl.queueDayRepositoryImplProvider.get(), singletonCImpl.settingsRepositoryImplProvider.get(), viewModelCImpl.callNextTokenUseCase(), viewModelCImpl.recallTokenUseCase(), viewModelCImpl.skipTokenUseCase(), viewModelCImpl.completeTokenUseCase());

          case 2: // com.cuecall.app.ui.screens.history.DailyHistoryViewModel 
          return (T) new DailyHistoryViewModel(singletonCImpl.tokenRepositoryImplProvider.get(), singletonCImpl.queueDayRepositoryImplProvider.get(), singletonCImpl.settingsRepositoryImplProvider.get(), singletonCImpl.serviceRepositoryImplProvider.get());

          case 3: // com.cuecall.app.ui.screens.display.DisplayViewModel 
          return (T) new DisplayViewModel(singletonCImpl.tokenRepositoryImplProvider.get(), singletonCImpl.queueDayRepositoryImplProvider.get(), singletonCImpl.settingsRepositoryImplProvider.get(), singletonCImpl.tokenSyncManagerProvider.get());

          case 4: // com.cuecall.app.ui.screens.settings.PrinterSettingsViewModel 
          return (T) new PrinterSettingsViewModel(singletonCImpl.settingsRepositoryImplProvider.get(), singletonCImpl.providePrinterManagerProvider.get(), singletonCImpl.provideBluetoothAdapterProvider.get());

          case 5: // com.cuecall.app.ui.screens.reception.ReceptionViewModel 
          return (T) new ReceptionViewModel(singletonCImpl.serviceRepositoryImplProvider.get(), singletonCImpl.settingsRepositoryImplProvider.get(), viewModelCImpl.generateTokenUseCase(), singletonCImpl.providePrinterManagerProvider.get());

          case 6: // com.cuecall.app.ui.screens.settings.ServiceManagementViewModel 
          return (T) new ServiceManagementViewModel(singletonCImpl.serviceRepositoryImplProvider.get(), singletonCImpl.settingsRepositoryImplProvider.get());

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ActivityRetainedCImpl extends CueCallApplication_HiltComponents.ActivityRetainedC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl = this;

    private Provider<ActivityRetainedLifecycle> provideActivityRetainedLifecycleProvider;

    private ActivityRetainedCImpl(SingletonCImpl singletonCImpl,
        SavedStateHandleHolder savedStateHandleHolderParam) {
      this.singletonCImpl = singletonCImpl;

      initialize(savedStateHandleHolderParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandleHolder savedStateHandleHolderParam) {
      this.provideActivityRetainedLifecycleProvider = DoubleCheck.provider(new SwitchingProvider<ActivityRetainedLifecycle>(singletonCImpl, activityRetainedCImpl, 0));
    }

    @Override
    public ActivityComponentBuilder activityComponentBuilder() {
      return new ActivityCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public ActivityRetainedLifecycle getActivityRetainedLifecycle() {
      return provideActivityRetainedLifecycleProvider.get();
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // dagger.hilt.android.ActivityRetainedLifecycle 
          return (T) ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory.provideActivityRetainedLifecycle();

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ServiceCImpl extends CueCallApplication_HiltComponents.ServiceC {
    private final SingletonCImpl singletonCImpl;

    private final ServiceCImpl serviceCImpl = this;

    private ServiceCImpl(SingletonCImpl singletonCImpl, Service serviceParam) {
      this.singletonCImpl = singletonCImpl;


    }
  }

  private static final class SingletonCImpl extends CueCallApplication_HiltComponents.SingletonC {
    private final ApplicationContextModule applicationContextModule;

    private final SingletonCImpl singletonCImpl = this;

    private Provider<AppDatabase> provideDatabaseProvider;

    private Provider<CounterRepositoryImpl> counterRepositoryImplProvider;

    private Provider<DataStore<Preferences>> provideDataStoreProvider;

    private Provider<SettingsRepositoryImpl> settingsRepositoryImplProvider;

    private Provider<FirebaseFirestore> provideFirestoreProvider;

    private Provider<FirestoreTokenSource> firestoreTokenSourceProvider;

    private Provider<TokenRepositoryImpl> tokenRepositoryImplProvider;

    private Provider<QueueDayRepositoryImpl> queueDayRepositoryImplProvider;

    private Provider<CallEventRepositoryImpl> callEventRepositoryImplProvider;

    private Provider<ServiceRepositoryImpl> serviceRepositoryImplProvider;

    private Provider<TokenSyncManager> tokenSyncManagerProvider;

    private Provider<MockPrinterManager> mockPrinterManagerProvider;

    private Provider<BluetoothAdapter> provideBluetoothAdapterProvider;

    private Provider<EscPosPrinterManager> escPosPrinterManagerProvider;

    private Provider<PrinterManager> providePrinterManagerProvider;

    private Provider<TokenSequenceRepositoryImpl> tokenSequenceRepositoryImplProvider;

    private SingletonCImpl(ApplicationContextModule applicationContextModuleParam) {
      this.applicationContextModule = applicationContextModuleParam;
      initialize(applicationContextModuleParam);

    }

    private CounterDao counterDao() {
      return DatabaseModule_ProvideCounterDaoFactory.provideCounterDao(provideDatabaseProvider.get());
    }

    private TokenDao tokenDao() {
      return DatabaseModule_ProvideTokenDaoFactory.provideTokenDao(provideDatabaseProvider.get());
    }

    private QueueDayDao queueDayDao() {
      return DatabaseModule_ProvideQueueDayDaoFactory.provideQueueDayDao(provideDatabaseProvider.get());
    }

    private CallEventDao callEventDao() {
      return DatabaseModule_ProvideCallEventDaoFactory.provideCallEventDao(provideDatabaseProvider.get());
    }

    private ServiceDao serviceDao() {
      return DatabaseModule_ProvideServiceDaoFactory.provideServiceDao(provideDatabaseProvider.get());
    }

    @SuppressWarnings("unchecked")
    private void initialize(final ApplicationContextModule applicationContextModuleParam) {
      this.provideDatabaseProvider = DoubleCheck.provider(new SwitchingProvider<AppDatabase>(singletonCImpl, 1));
      this.counterRepositoryImplProvider = DoubleCheck.provider(new SwitchingProvider<CounterRepositoryImpl>(singletonCImpl, 0));
      this.provideDataStoreProvider = DoubleCheck.provider(new SwitchingProvider<DataStore<Preferences>>(singletonCImpl, 3));
      this.settingsRepositoryImplProvider = DoubleCheck.provider(new SwitchingProvider<SettingsRepositoryImpl>(singletonCImpl, 2));
      this.provideFirestoreProvider = DoubleCheck.provider(new SwitchingProvider<FirebaseFirestore>(singletonCImpl, 6));
      this.firestoreTokenSourceProvider = DoubleCheck.provider(new SwitchingProvider<FirestoreTokenSource>(singletonCImpl, 5));
      this.tokenRepositoryImplProvider = DoubleCheck.provider(new SwitchingProvider<TokenRepositoryImpl>(singletonCImpl, 4));
      this.queueDayRepositoryImplProvider = DoubleCheck.provider(new SwitchingProvider<QueueDayRepositoryImpl>(singletonCImpl, 7));
      this.callEventRepositoryImplProvider = DoubleCheck.provider(new SwitchingProvider<CallEventRepositoryImpl>(singletonCImpl, 8));
      this.serviceRepositoryImplProvider = DoubleCheck.provider(new SwitchingProvider<ServiceRepositoryImpl>(singletonCImpl, 9));
      this.tokenSyncManagerProvider = DoubleCheck.provider(new SwitchingProvider<TokenSyncManager>(singletonCImpl, 10));
      this.mockPrinterManagerProvider = DoubleCheck.provider(new SwitchingProvider<MockPrinterManager>(singletonCImpl, 12));
      this.provideBluetoothAdapterProvider = DoubleCheck.provider(new SwitchingProvider<BluetoothAdapter>(singletonCImpl, 14));
      this.escPosPrinterManagerProvider = DoubleCheck.provider(new SwitchingProvider<EscPosPrinterManager>(singletonCImpl, 13));
      this.providePrinterManagerProvider = DoubleCheck.provider(new SwitchingProvider<PrinterManager>(singletonCImpl, 11));
      this.tokenSequenceRepositoryImplProvider = DoubleCheck.provider(new SwitchingProvider<TokenSequenceRepositoryImpl>(singletonCImpl, 15));
    }

    @Override
    public void injectCueCallApplication(CueCallApplication cueCallApplication) {
    }

    @Override
    public Set<Boolean> getDisableFragmentGetContextFix() {
      return ImmutableSet.<Boolean>of();
    }

    @Override
    public ActivityRetainedComponentBuilder retainedComponentBuilder() {
      return new ActivityRetainedCBuilder(singletonCImpl);
    }

    @Override
    public ServiceComponentBuilder serviceComponentBuilder() {
      return new ServiceCBuilder(singletonCImpl);
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.cuecall.app.data.repository.CounterRepositoryImpl 
          return (T) new CounterRepositoryImpl(singletonCImpl.counterDao());

          case 1: // com.cuecall.app.data.local.AppDatabase 
          return (T) DatabaseModule_ProvideDatabaseFactory.provideDatabase(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 2: // com.cuecall.app.data.repository.SettingsRepositoryImpl 
          return (T) new SettingsRepositoryImpl(singletonCImpl.provideDataStoreProvider.get());

          case 3: // androidx.datastore.core.DataStore<androidx.datastore.preferences.core.Preferences> 
          return (T) DatabaseModule_ProvideDataStoreFactory.provideDataStore(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 4: // com.cuecall.app.data.repository.TokenRepositoryImpl 
          return (T) new TokenRepositoryImpl(singletonCImpl.tokenDao(), singletonCImpl.firestoreTokenSourceProvider.get());

          case 5: // com.cuecall.app.data.remote.source.FirestoreTokenSource 
          return (T) new FirestoreTokenSource(singletonCImpl.provideFirestoreProvider.get());

          case 6: // com.google.firebase.firestore.FirebaseFirestore 
          return (T) FirestoreModule_ProvideFirestoreFactory.provideFirestore();

          case 7: // com.cuecall.app.data.repository.QueueDayRepositoryImpl 
          return (T) new QueueDayRepositoryImpl(singletonCImpl.queueDayDao());

          case 8: // com.cuecall.app.data.repository.CallEventRepositoryImpl 
          return (T) new CallEventRepositoryImpl(singletonCImpl.callEventDao());

          case 9: // com.cuecall.app.data.repository.ServiceRepositoryImpl 
          return (T) new ServiceRepositoryImpl(singletonCImpl.serviceDao());

          case 10: // com.cuecall.app.sync.TokenSyncManager 
          return (T) new TokenSyncManager(singletonCImpl.firestoreTokenSourceProvider.get(), singletonCImpl.tokenDao());

          case 11: // com.cuecall.app.printer.PrinterManager 
          return (T) PrinterModule_ProvidePrinterManagerFactory.providePrinterManager(singletonCImpl.mockPrinterManagerProvider.get(), singletonCImpl.escPosPrinterManagerProvider.get());

          case 12: // com.cuecall.app.printer.MockPrinterManager 
          return (T) new MockPrinterManager();

          case 13: // com.cuecall.app.printer.EscPosPrinterManager 
          return (T) new EscPosPrinterManager(singletonCImpl.provideBluetoothAdapterProvider.get());

          case 14: // android.bluetooth.BluetoothAdapter 
          return (T) PrinterModule.INSTANCE.provideBluetoothAdapter(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 15: // com.cuecall.app.data.repository.TokenSequenceRepositoryImpl 
          return (T) new TokenSequenceRepositoryImpl(singletonCImpl.firestoreTokenSourceProvider.get());

          default: throw new AssertionError(id);
        }
      }
    }
  }
}
