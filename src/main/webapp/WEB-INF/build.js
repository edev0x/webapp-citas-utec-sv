const esbuild = require('esbuild');

esbuild.build({
  entryPoints: ['scripts/app.js'],
  bundle: true,
  minify: true,
  outfile: '../public/assets/js/app.bundle.js',
  platform: 'browser',
  target: 'es2020',
  format: "iife"
}).catch(() => process.exit(1));