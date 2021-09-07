import React from 'react';

function TemplateBox({ json }) {
  console.log(json);
  return (
    <pre>
      {JSON.stringify(json, null, 2)}
    </pre>
  );
}

export default TemplateBox;
