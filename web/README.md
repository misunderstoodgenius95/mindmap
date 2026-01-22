# Vue Slideshow

A beautiful, interactive slideshow component built with Vue 3.

## Features

- **Smooth Transitions**: Slide animations when navigating between slides
- **Keyboard Navigation**: Use arrow keys to navigate, spacebar to play/pause
- **Auto-play Mode**: Automatic slideshow with configurable interval
- **Progress Tracking**: Visual progress bar and slide counter
- **Dot Indicators**: Click to jump to any slide
- **Responsive Design**: Works on desktop and mobile devices
- **Customizable**: Easy to customize slides and styling

## Getting Started

### Installation

```bash
cd web
npm install
```

### Development

Run the development server:

```bash
npm run dev
```

The slideshow will be available at `http://localhost:3000`

### Build for Production

```bash
npm run build
```

The built files will be in the `dist` directory.

## Usage

### Basic Usage

```vue
<template>
  <Slideshow :slides="slides" />
</template>

<script>
import Slideshow from './components/Slideshow.vue'

export default {
  components: { Slideshow },
  data() {
    return {
      slides: [
        {
          title: 'First Slide',
          content: 'This is the content of the first slide.'
        },
        {
          title: 'Second Slide',
          content: 'This is the content of the second slide.'
        }
      ]
    }
  }
}
</script>
```

### Props

- `slides` (Array): Array of slide objects with `title` and `content` properties
- `autoPlayInterval` (Number): Interval in milliseconds for auto-play (default: 3000)

### Keyboard Controls

- **Arrow Right**: Next slide
- **Arrow Left**: Previous slide
- **Space**: Toggle auto-play

## Customization

You can customize the slideshow by modifying the `Slideshow.vue` component:

- **Colors**: Change the gradient colors in the `.slideshow-wrapper` class
- **Transitions**: Modify the transition animations
- **Timing**: Adjust the `autoPlayInterval` prop
- **Styling**: Update the scoped styles to match your design

## Integration with Mind Map

This slideshow can be integrated with the JavaFX Mind Map application to present mind map nodes in a sequential format. To do this:

1. Export mind map data from the JavaFX application
2. Transform the data into the slide format
3. Pass the slides to the Slideshow component

## License

This project is open source and available for educational purposes.
