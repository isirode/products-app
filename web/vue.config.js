// const { defineConfig } = require('@vue/cli-service')

const MonacoWebpackPlugin = require('monaco-editor-webpack-plugin')

// const path = require('path')

/*
module.exports = defineConfig({
  transpileDependencies: [
    'quasar'
  ],

  pluginOptions: {
    quasar: {
      importStrategy: 'kebab',
      rtlSupport: false
    }
  },
  configureWebpack: {
    plugins: [
      new MonacoWebpackPlugin({
        languages: ['sql']
      })
    ]
  }
})
*/
module.exports = {
  transpileDependencies: [
    'quasar'
  ],

  pluginOptions: {
    quasar: {
      importStrategy: 'kebab',
      rtlSupport: false
    }
  },
  configureWebpack: {
    resolve: {
      alias: {
        'editor.worker': 'node_modules/monaco-editor/esm/vs/editor/editor.worker.js'
      }
    },
    plugins: [
      new MonacoWebpackPlugin({
        languages: ['sql']
      })
    ]
  }
}
