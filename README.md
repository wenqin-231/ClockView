# ClockView
a simple clock to change time with animation.

## Normal

You can use it in normal way with transparent dail color just like this:

[![image](https://raw.githubusercontent.com/Wenqin-231/ClockView/master/art/normal.gif)](https://github.com/Wenqin-231/ClockView/blob/master/art/normal.gif)



## Muti-style

You can change the style in xml or in code so that set the different clock view.

I set the sample to show the different color in ColorView without hour point.

[![image](https://raw.githubusercontent.com/Wenqin-231/ClockView/master/art/multi-style.gif)](https://github.com/Wenqin-231/ClockView/blob/master/art/multi-style.gif)

## Usage

In code:

```
clockView.setDialColor(ContextCompat.getColor(this, R.color.blue))
        .setMinuteColor(Color.WHITE)
        .setClockBorderColor(Color.WHITE)
        ...
        .draw();
```

In xml :

```
<com.lewis.clockview.ClockView
    android:layout_width="70dp"
    android:layout_height="70dp"
    app:dialColor="@color/colorAccent"
    app:isShowHour="false"
    ...
    />
```



## How to

To get a Git project into your build:

**Step 1.** Add the JitPack repository to your build file

```
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

**Step 2.** Add the dependency

```
	dependencies {
		compile 'com.github.wenqin-231:ClockView:v0.35'
	}
```

## More

Hope you would like it.
