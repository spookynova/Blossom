# General configuration
-dontwarn com.squareup.okhttp.**
-dontwarn okhttp3.**
-dontwarn okio.**

# Optimization options
-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontpreverify
-verbose

# Obfuscation options
-renamesourcefileattribute SourceFile
-keepattributes SourceFile,LineNumberTable
-keepattributes Signature
-keepattributes *Annotation*
-keepparameternames
-keep class **.R
-keepclassmembers class **.R$* {
    public static <fields>;
}


# Keep Android specific classes and methods
-keep class android.support.** { *; }
-keep interface android.support.** { *; }
-keep class androidx.annotation.** { *; }
-keep class androidx.appcompat.widget.** { *; }
-keep class androidx.lifecycle.** { *; }

# Keep specific libraries (adjust as needed)
-keep class com.google.gson.** { *; }
-keep class org.json.** { *; }


# Keep your model classes and fields (adjust as needed)
-keep class id.blossom.data.model.** { *; }

