package com.cuecall.app.data.remote.source;

import com.google.firebase.firestore.FirebaseFirestore;
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
public final class FirestoreTokenSource_Factory implements Factory<FirestoreTokenSource> {
  private final Provider<FirebaseFirestore> firestoreProvider;

  public FirestoreTokenSource_Factory(Provider<FirebaseFirestore> firestoreProvider) {
    this.firestoreProvider = firestoreProvider;
  }

  @Override
  public FirestoreTokenSource get() {
    return newInstance(firestoreProvider.get());
  }

  public static FirestoreTokenSource_Factory create(Provider<FirebaseFirestore> firestoreProvider) {
    return new FirestoreTokenSource_Factory(firestoreProvider);
  }

  public static FirestoreTokenSource newInstance(FirebaseFirestore firestore) {
    return new FirestoreTokenSource(firestore);
  }
}
